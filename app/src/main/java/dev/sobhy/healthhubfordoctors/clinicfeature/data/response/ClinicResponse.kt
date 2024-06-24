package dev.sobhy.healthhubfordoctors.clinicfeature.data.response

data class ClinicResponse(
    val id: Int,
    val doctorId: String,
    val name: String,
    val phone: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val examination: Double,
    val followUp: Double,
    val doctorAvailabilities: List<AvailabilityResponse>?,
)
