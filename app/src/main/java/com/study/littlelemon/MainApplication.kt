package com.study.littlelemon

import android.app.Application
import com.study.littlelemon.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}