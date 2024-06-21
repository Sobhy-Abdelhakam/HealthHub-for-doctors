package dev.sobhy.healthhubfordoctors.clinicfeature.data.model

import java.time.LocalTime

data class DayState(
    val isAvailable: Boolean,
    val from: LocalTime,
    val to: LocalTime,
)
