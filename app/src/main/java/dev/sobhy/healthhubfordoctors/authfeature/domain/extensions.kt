package dev.sobhy.healthhubfordoctors.authfeature.domain

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.ClinicRequest
import dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.AddClinicState

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
