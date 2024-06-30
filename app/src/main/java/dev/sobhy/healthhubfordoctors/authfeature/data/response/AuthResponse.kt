package dev.sobhy.healthhubfordoctors.authfeature.data.response

data class AuthResponse(
    val message: String?,
    val body: AuthBody?,
    val ok: Boolean,
    val status: Int,
    val error: String? = null,
)
