package dev.sobhy.healthhubfordoctors.clinicfeature.data.response

data class DoctorAvailabilitiesResponse(
    val id: Int,
    val day: String,
    val startTime: String,
    val endTime: String,
    val available: Boolean,
)
