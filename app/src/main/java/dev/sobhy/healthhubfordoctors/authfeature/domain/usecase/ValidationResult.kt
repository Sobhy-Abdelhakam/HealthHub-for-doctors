package dev.sobhy.healthhubfordoctors.authfeature.domain.usecase

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
)
