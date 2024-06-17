package dev.sobhy.healthhubfordoctors.authfeature.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)
