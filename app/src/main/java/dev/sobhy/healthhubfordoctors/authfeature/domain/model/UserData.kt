package dev.sobhy.healthhubfordoctors.authfeature.domain.model

data class UserData(
    val name: String,
    val email: String,
    val phone: String,
    val gender: String,
    val dateOfBirth: String,
    val specialization: String,
    val professionalTitle: String,
)
