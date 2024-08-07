package dev.sobhy.healthhubfordoctors.profilefeature.domain.model

data class DoctorProfileUiModel(
    val name: String = "",
    val specialty: String = "",
    val imgPath: String? = null,
    val rating: Double = 0.0,
)
