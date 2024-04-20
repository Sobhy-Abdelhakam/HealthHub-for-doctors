package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

import java.time.DayOfWeek
import java.time.LocalTime

data class AddClinicState(
    val clinicName: String = "",
    val clinicNumber: String = "",
    val clinicAddress: String = "",
    val availability: Availability = Availability(),
)

data class Availability(
    val dayAvailable: Map<DayOfWeek, DayState> =
        mapOf(
            DayOfWeek.MONDAY to DayState(),
            DayOfWeek.TUESDAY to DayState(),
            DayOfWeek.WEDNESDAY to DayState(),
            DayOfWeek.THURSDAY to DayState(),
            DayOfWeek.FRIDAY to DayState(),
            DayOfWeek.SATURDAY to DayState(),
            DayOfWeek.SUNDAY to DayState(),
        ),
)

data class DayState(
    val isSwitchOn: Boolean = false,
    val from: LocalTime = LocalTime.of(6, 0),
    val to: LocalTime = LocalTime.of(6, 0),
)
