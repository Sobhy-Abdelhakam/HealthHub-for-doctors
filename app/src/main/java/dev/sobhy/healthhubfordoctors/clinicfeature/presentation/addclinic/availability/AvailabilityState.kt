package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.availability

import java.time.DayOfWeek
import java.time.LocalTime

data class AvailabilityState(
    val dayAvailable: Map<DayOfWeek, DayStateUi> = DayOfWeek.entries.associateWith { DayStateUi() },
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

data class DayStateUi(
    val isSwitchOn: Boolean = false,
    val from: LocalTime = LocalTime.of(6, 0),
    val to: LocalTime = LocalTime.of(6, 0),
)
