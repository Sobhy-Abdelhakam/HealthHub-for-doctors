package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.availability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sobhy.healthhubfordoctors.authfeature.domain.toDayState
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Availability
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.AvailabilityUseCase
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime

class AvailabilityViewModel(
    private val availabilityUseCase: AvailabilityUseCase,
) : ViewModel() {
    private val _availability = MutableStateFlow(AvailabilityState())
    val availabilityState = _availability.asStateFlow()

    fun onSwitchChanged(
        day: DayOfWeek,
        isChecked: Boolean,
    ) {
        _availability.update {
            val updatedDay = it.dayAvailable[day]?.copy(isSwitchOn = isChecked) ?: DayStateUi()
            it.copy(dayAvailable = it.dayAvailable + (day to updatedDay))
        }
    }

    fun onFromChange(
        day: DayOfWeek,
        from: LocalTime,
    ) {
        _availability.update { currentState ->
            val updatedDayState = currentState.dayAvailable[day]?.copy(from = from) ?: DayStateUi()
            currentState.copy(dayAvailable = currentState.dayAvailable + (day to updatedDayState))
        }
    }

    fun onToChange(
        day: DayOfWeek,
        to: LocalTime,
    ) {
        _availability.update { currentState ->
            val updatedDayState = currentState.dayAvailable[day]?.copy(to = to) ?: DayStateUi()
            currentState.copy(dayAvailable = currentState.dayAvailable + (day to updatedDayState))
        }
    }

    fun setAvailability() {
        viewModelScope.launch {
            _availability.update { it.copy(isLoading = true) }
            val availabilityMap =
                _availability.value.dayAvailable.mapValues {
                    it.value.copy(
                        isSwitchOn = it.value.isSwitchOn,
                        from = it.value.from,
                        to = it.value.to,
                    ).toDayState()
                }
            availabilityUseCase(Availability(availabilityMap)).collect { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _availability.update { it.copy(isLoading = false) }
                    }
                    is Resource.Error -> {
                        _availability.update { it.copy(isLoading = false, errorMessage = result.message) }
                    }
                }
            }

//            val addClinicState = AddClinicState(availability = availabilityMap)
//            when (val result = clinicRepository.saveClinic(addClinicState)) {
//                is Result.Success -> {
//                    _availabilityState.update { it.copy(isLoading = false) }
//                }
//                is Result.Failure -> {
//                    _availabilityState.update { it.copy(isLoading = false, errorMessage = result.exception.message) }
//                }
//            }
        }
    }
}
