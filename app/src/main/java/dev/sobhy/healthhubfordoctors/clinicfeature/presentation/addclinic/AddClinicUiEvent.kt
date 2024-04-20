package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

import java.time.DayOfWeek
import java.time.LocalTime

sealed class AddClinicUiEvent() {
    data class ClinicNameChange(val name: String) : AddClinicUiEvent()

    data class ClinicPhoneChange(val phone: String) : AddClinicUiEvent()

    data class ClinicAddressChange(val address: String) : AddClinicUiEvent()

    data class UpdateSwitchState(val day: DayOfWeek, val newState: Boolean) : AddClinicUiEvent()

    data class UpdateFrom(val day: DayOfWeek, val fromText: LocalTime) : AddClinicUiEvent()

    data class UpdateTo(val day: DayOfWeek, val toText: LocalTime) : AddClinicUiEvent()

    data object SaveClinic : AddClinicUiEvent()
}
