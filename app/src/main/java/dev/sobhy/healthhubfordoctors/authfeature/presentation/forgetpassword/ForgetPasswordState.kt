package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

data class ForgetPasswordState(
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)
