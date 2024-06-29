package dev.sobhy.healthhubfordoctors.clinicfeature.data.model.response

data class AvailabilityResponse(
    val id: Int,
    val day: String,
    val startTime: String,
    val endTime: String,
    val available: Boolean,
)
