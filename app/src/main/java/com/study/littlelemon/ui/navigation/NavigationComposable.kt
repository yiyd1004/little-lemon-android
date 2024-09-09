package com.study.littlelemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.study.littlelemon.navigation.Destinations
import com.study.littlelemon.ui.screen.Home
import com.study.littlelemon.ui.screen.Onboarding
import com.study.littlelemon.ui.screen.Profile
import com.study.littlelemon.viewmodel.NavigationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(
    navHostController: NavHostController,
    navViewModel: NavigationViewModel = koinViewModel()
) {
    val navState = navViewModel.navState.collectAsStateWithLifecycle().value

    NavHost(
        navController = navHostController,
        startDestination = navState.startDestination.route
    ) {
        composable(route = Destinations.HOME.route) {
            Home(navHostController)
        }

        composable(route = Destinations.PROFILE.route) {
            Profile(navHostController)
        }

        composable(route = Destinations.ON_BOARDING.route) {
            Onboarding(navHostController)
        }
    }
}