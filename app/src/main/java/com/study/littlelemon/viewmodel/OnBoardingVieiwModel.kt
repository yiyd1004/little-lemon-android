package com.study.littlelemon.viewmodel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.study.littlelemon.data.Repository
import com.study.littlelemon.database.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OnBoardingUiState (
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val registerStatus: Boolean = false
)

class OnBoardingViewModel(
    private val repository: Repository
): ViewModel() {

    private val _uiState: MutableStateFlow<OnBoardingUiState> = MutableStateFlow(OnBoardingUiState())
    val uiState = _uiState.asStateFlow()

    fun handleRegister(): Boolean {
        val isUserNameValid: Boolean = _uiState.value.run {
            firstName.isNotBlank() && lastName.isNotBlank()
        }
        val isEmailValid: Boolean = _uiState.value.run {
            isValidEmail(email)
        }

        if (isUserNameValid && isEmailValid) {
            repository.setUserLogIn(true)
            _uiState.update {
                it.copy(registerStatus = true)
            }
            with(_uiState.value) {
                repository.setUserData(firstName, lastName, email)
            }
        } else {
            repository.setUserLogIn(false)
            _uiState.update {
                it.copy(registerStatus = false)
            }
        }

        return isUserNameValid && isEmailValid
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun updateFirstName(firstName: String) {
        _uiState.update {
            it.copy(firstName = firstName)
        }
    }

    fun updateLastName(lastName: String) {
        _uiState.update {
            it.copy(lastName = lastName)
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }
}