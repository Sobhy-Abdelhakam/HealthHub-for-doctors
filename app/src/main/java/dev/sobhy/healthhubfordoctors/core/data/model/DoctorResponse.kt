package dev.sobhy.healthhubfordoctors.core.data.model

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.response.ClinicResponse

data class DoctorResponse(
    val id: Int,
    val name: String,
    val birthDate: String,
    val phoneNumber: String,
    val email: String,
    val gender: String,
    val imgPath: String?,
    val specialty: String,
    val profTitle: String,
    val rating: Double,
    val clinics: List<ClinicResponse>,
)
