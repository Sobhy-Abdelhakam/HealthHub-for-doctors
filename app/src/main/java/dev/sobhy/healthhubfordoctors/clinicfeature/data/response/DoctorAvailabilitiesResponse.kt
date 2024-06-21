package dev.sobhy.healthhubfordoctors.clinicfeature.data.response

import java.time.LocalTime

data class DoctorAvailabilitiesResponse(
    val id: Int,
    val day: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val available: Boolean,
)
