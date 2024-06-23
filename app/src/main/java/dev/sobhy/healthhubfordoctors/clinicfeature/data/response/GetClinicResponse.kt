package dev.sobhy.healthhubfordoctors.clinicfeature.data.response

data class GetClinicResponse(
    val id: Int,
    val name: String,
    val phone: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val examination: Double,
    val followUp: Double,
    val doctorAvailabilities: List<DoctorAvailabilitiesResponse>?,
)
