package dev.sobhy.healthhubfordoctors.authfeature.data.request

data class ResetPassRequest(
    val email: String,
    val otp: String,
    val newPassword: String,
)

data class EmailRequest(
    val email: String,
)
