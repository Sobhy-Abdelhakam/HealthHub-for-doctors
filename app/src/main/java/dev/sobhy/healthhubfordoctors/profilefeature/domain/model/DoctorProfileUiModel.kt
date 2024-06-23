package dev.sobhy.healthhubfordoctors.profilefeature.domain.model

data class DoctorProfileUiModel(
    val name: String = "",
    val specialization: String = "",
    val profilePicture: String? = null,
    val rating: Double = 0.0,
)
