package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import java.time.LocalDate

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val gender: String = "",
    val dateOfBirth: LocalDate = LocalDate.of(1000, 1, 1),
    val specialization: String = "",
    val professionalTitle: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
)
