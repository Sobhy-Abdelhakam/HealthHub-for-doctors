package dev.sobhy.healthhubfordoctors.authfeature.data.remote

import java.time.LocalDate

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val gender: String,
    val dateOfBirth: LocalDate,
    val specialization: String,
    val professionalTitle: String,
    val password: String,
)
