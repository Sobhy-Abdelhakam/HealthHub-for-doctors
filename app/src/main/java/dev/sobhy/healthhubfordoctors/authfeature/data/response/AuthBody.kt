package dev.sobhy.healthhubfordoctors.authfeature.data.response

data class AuthBody(
    val username: String,
    val userId: Int,
    val accessToken: String?,
    val refreshToken: String?,
    val expirationDate: String?,
)
