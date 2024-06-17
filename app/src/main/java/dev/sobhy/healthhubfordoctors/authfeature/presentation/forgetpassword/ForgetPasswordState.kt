package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

data class ForgetPasswordState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
)
