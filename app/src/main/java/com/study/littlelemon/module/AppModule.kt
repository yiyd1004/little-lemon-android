package com.study.littlelemon.module

import android.content.Context
import android.content.SharedPreferences
import com.study.littlelemon.data.Repository
import com.study.littlelemon.data.RepositoryImpl
import com.study.littlelemon.database.DatabaseManager
import com.study.littlelemon.database.DatabaseManagerImpl
import com.study.littlelemon.database.DatabaseService
import com.study.littlelemon.database.LittleLemonDB
import com.study.littlelemon.network.KtorClient
import com.study.littlelemon.network.MenuService
import com.study.littlelemon.network.MenuServiceImpl
import com.study.littlelemon.network.NetworkDataManager
import com.study.littlelemon.network.NetworkDataManagerImpl
import com.study.littlelemon.viewmodel.HomeViewModel
import com.study.littlelemon.viewmodel.NavigationState
import com.study.littlelemon.viewmodel.NavigationViewModel
import com.study.littlelemon.viewmodel.OnBoardingViewModel
import com.study.littlelemon.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<MenuService> {
        MenuServiceImpl(
            httpClient = KtorClient.build()
        )
    }

    single(qualifier = named("LittleLemonDB")) {
        DatabaseService.build(context = androidContext())
    }

    single<DatabaseManager> {
        DatabaseManagerImpl(
            sharedPref = androidContext().getSharedPreferences("little_lemon_shared_prefs", Context.MODE_PRIVATE),
            database = get<LittleLemonDB>(qualifier = named("LittleLemonDB")).getMenuDao()
        )
    }

    factory<NetworkDataManager> {
        NetworkDataManagerImpl(service = get())
    }

    factory<Repository> {
        RepositoryImpl(
            dispatcher = Dispatchers.IO,
            networkDataManager = get(),
            databaseManager = get()
        )
    }

    viewModel {
        HomeViewModel(repository = get())
    }

    viewModel {
        OnBoardingViewModel(repository = get())
    }

    viewModel {
        ProfileViewModel(repository = get())
    }

    viewModel {
        NavigationViewModel(repository = get())
    }
}