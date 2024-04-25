package dev.sobhy.healthhubfordoctors.authfeature.data.remote

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val gender: String,
    val dateOfBirth: Long,
    val specialization: String,
    val professionalTitle: String,
    val password: String,
)
