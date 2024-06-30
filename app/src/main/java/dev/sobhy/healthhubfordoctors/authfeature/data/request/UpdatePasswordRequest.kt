package dev.sobhy.healthhubfordoctors.authfeature.data.request

data class UpdatePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
)
