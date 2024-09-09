package com.study.littlelemon.viewmodel

import androidx.lifecycle.ViewModel
import com.study.littlelemon.data.Repository
import com.study.littlelemon.navigation.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class NavigationState (
    val startDestination: Destinations = Destinations.HOME
)

class NavigationViewModel(
    private val repository: Repository
): ViewModel() {
    private val _navState: MutableStateFlow<NavigationState> = MutableStateFlow(NavigationState())
    val navState: StateFlow<NavigationState> = _navState.asStateFlow()

    init {
        updateStartDestination(repository.isUserLogIn())
    }

    private fun updateStartDestination(isUserLogged: Boolean) {
        val startDest = when (isUserLogged) {
            true -> Destinations.HOME
            else -> Destinations.ON_BOARDING
        }

        _navState.update {
            it.copy(
                startDestination = startDest
            )
        }
    }
}