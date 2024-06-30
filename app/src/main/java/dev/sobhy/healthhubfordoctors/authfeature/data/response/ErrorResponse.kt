package dev.sobhy.healthhubfordoctors.authfeature.data.response

data class ErrorResponse(
    val timestamp: String,
    val message: String,
    val details: String,
)
