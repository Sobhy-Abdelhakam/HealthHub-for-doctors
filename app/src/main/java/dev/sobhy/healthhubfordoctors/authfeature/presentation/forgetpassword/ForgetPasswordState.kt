package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

data class ForgetPasswordState(
    val email: String = "",
    val otp: String = "",
    val newPassword: String = "",
    val isLoading: Boolean = false,
    val isOtpSent: Boolean = false,
    val changeSuccess: Boolean = false,
    val error: String? = null,
)
