package dev.sobhy.healthhubfordoctors.authfeature.data.remote

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val phone: String,
    val gender: String,
    val dateOfBirth: String,
    val specialization: String,
    val professionalTitle: String,
)
