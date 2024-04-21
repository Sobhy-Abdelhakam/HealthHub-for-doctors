package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalTime

class AddClinicViewModel : ViewModel() {
    private val _addClinicState = MutableStateFlow(AddClinicState())
    val addClinicState = _addClinicState.asStateFlow()

    fun onEvent(event: AddClinicUiEvent) {
        when (event) {
            is AddClinicUiEvent.ClinicNameChange ->
                _addClinicState.update {
                    it.copy(clinicName = event.name)
                }
            is AddClinicUiEvent.ClinicPhoneChange ->
                _addClinicState.update {
                    it.copy(clinicNumber = event.phone)
                }
            is AddClinicUiEvent.ClinicAddressChange ->
                _addClinicState.update {
                    it.copy(clinicAddress = event.address)
                }
            is AddClinicUiEvent.ExaminationChange ->
                _addClinicState.update {
                    it.copy(examination = event.examination)
                }
            is AddClinicUiEvent.FollowUpChange ->
                _addClinicState.update {
                    it.copy(followUp = event.followUp)
                }
            is AddClinicUiEvent.UpdateSwitchState -> updateSwitchState(event.newState, event.day)
            is AddClinicUiEvent.UpdateFrom -> updateFromText(event.fromText, event.day)
            is AddClinicUiEvent.UpdateTo -> updateToText(event.toText, event.day)
            AddClinicUiEvent.SaveClinic -> TODO()
        }
    }

    private fun updateSwitchState(
        newState: Boolean,
        day: DayOfWeek,
    ) {
        val updatedDayAvailable = _addClinicState.value.availability.dayAvailable.toMutableMap()
        updatedDayAvailable[day] = updatedDayAvailable[day]!!.copy(isSwitchOn = newState)
        _addClinicState.value = _addClinicState.value.copy(availability = Availability(updatedDayAvailable))
    }

    private fun updateFromText(
        fromText: LocalTime,
        day: DayOfWeek,
    ) {
        val updatedDayAvailable = _addClinicState.value.availability.dayAvailable.toMutableMap()
        updatedDayAvailable[day] = updatedDayAvailable[day]!!.copy(from = fromText)
        _addClinicState.value = _addClinicState.value.copy(availability = Availability(updatedDayAvailable))
    }

    private fun updateToText(
        toText: LocalTime,
        day: DayOfWeek,
    ) {
        val updatedDayAvailable = _addClinicState.value.availability.dayAvailable.toMutableMap()
        updatedDayAvailable[day] = updatedDayAvailable[day]!!.copy(to = toText)
        _addClinicState.value = _addClinicState.value.copy(availability = Availability(updatedDayAvailable))
    }
}
