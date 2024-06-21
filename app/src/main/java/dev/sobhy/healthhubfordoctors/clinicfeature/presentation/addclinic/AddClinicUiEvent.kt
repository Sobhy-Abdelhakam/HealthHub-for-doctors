package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

sealed class AddClinicUiEvent() {
    data class ClinicNameChange(val name: String) : AddClinicUiEvent()

    data class ClinicPhoneChange(val phone: String) : AddClinicUiEvent()

    data class ClinicAddressChange(val address: String) : AddClinicUiEvent()

    data class UpdateLocation(val latitude: Double, val longitude: Double) : AddClinicUiEvent()

    data class ExaminationChange(val examination: String) : AddClinicUiEvent()

    data class FollowUpChange(val followUp: String) : AddClinicUiEvent()

    data object SaveClinic : AddClinicUiEvent()
}
