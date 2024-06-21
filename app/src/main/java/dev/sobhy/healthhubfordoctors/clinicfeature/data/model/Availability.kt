package dev.sobhy.healthhubfordoctors.clinicfeature.data.model

import java.time.DayOfWeek

data class Availability(
    val availability: Map<DayOfWeek, DayState>,
)
