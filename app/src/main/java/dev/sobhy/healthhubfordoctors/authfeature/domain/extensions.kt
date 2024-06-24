package dev.sobhy.healthhubfordoctors.authfeature.domain

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Availability
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.ClinicRequest
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.DayState
import dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.AddClinicState
import dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.availability.AvailabilityState
import dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.availability.DayStateUi

fun AddClinicState.toClinic(): ClinicRequest {
    return ClinicRequest(
        name = this.clinicName,
        address = this.clinicAddress,
        phone = this.clinicNumber,
        latitude = this.latitude,
        longitude = this.longitude,
        examination = this.examination,
        followUp = this.followUp,
    )
}

fun AvailabilityState.toAvailability(): Availability {
    return Availability(
        availability = dayAvailable.mapValues { it.value.toDayState() },
    )
}

fun DayStateUi.toDayState(): DayState {
    return DayState(
        status = isSwitchOn,
        from = this.from.toString(),
        to = this.to.toString(),
    )
}
