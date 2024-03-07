package dev.sobhy.healthhubfordoctors.authfeature.presentation.login

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    data object Login : LoginUiEvent()
}