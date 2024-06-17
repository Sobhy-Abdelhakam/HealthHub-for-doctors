package dev.sobhy.healthhubfordoctors.authfeature.presentation.login

sealed class LoginUiEvent {
    data class UpdateEmail(val email: String) : LoginUiEvent()

    data class UpdatePassword(val password: String) : LoginUiEvent()

    data object Login : LoginUiEvent()
}
