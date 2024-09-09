package com.study.littlelemon.viewmodel

import androidx.lifecycle.ViewModel
import com.study.littlelemon.data.Repository
import com.study.littlelemon.database.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileUiState (
    val firstName: String = "",
    val lastName: String = "",
    val email: String = ""
)

class ProfileViewModel(
    private val repository: Repository
): ViewModel() {
    
    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        val user: UserEntity = repository.getUserData()
        _uiState.update {
            it.copy(
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email
            )
        }
    }

    fun handleLogout() {
        repository.setUserLogIn(false)
        _uiState.update {
            it.copy(firstName = "", lastName = "", email = "")
        }
    }
}