package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.availability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Availability
import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.DayState
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.AvailabilityUseCase
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.CurrentAvailabilityUseCase
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AvailabilityViewModel
    @Inject
    constructor(
        private val availabilityUseCase: AvailabilityUseCase,
        private val currentAvailabilityUseCase: CurrentAvailabilityUseCase,
    ) : ViewModel() {
        private val _availabilityStateFlow = MutableStateFlow(AvailabilityState())
        val availabilityStateFlow = _availabilityStateFlow.asStateFlow()

        fun onSwitchChanged(
            day: DayOfWeek,
            isChecked: Boolean,
        ) {
            _availabilityStateFlow.update {
                val updatedDay = it.dayAvailable[day]?.copy(isSwitchOn = isChecked) ?: DayStateUi()
                it.copy(dayAvailable = it.dayAvailable + (day to updatedDay))
            }
        }

        fun onFromChange(
            day: DayOfWeek,
            from: LocalTime,
        ) {
            _availabilityStateFlow.update { currentState ->
                val updatedDayState = currentState.dayAvailable[day]?.copy(from = from) ?: DayStateUi()
                currentState.copy(dayAvailable = currentState.dayAvailable + (day to updatedDayState))
            }
        }

        fun onToChange(
            day: DayOfWeek,
            to: LocalTime,
        ) {
            _availabilityStateFlow.update { currentState ->
                val updatedDayState = currentState.dayAvailable[day]?.copy(to = to) ?: DayStateUi()
                currentState.copy(dayAvailable = currentState.dayAvailable + (day to updatedDayState))
            }
        }

        fun setAvailability(clinicId: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                _availabilityStateFlow.update { it.copy(isLoading = true) }
                val availabilityMap =
                    _availabilityStateFlow.value.dayAvailable.mapValues {
                        DayState(
                            status = it.value.isSwitchOn,
                            from = it.value.from.toString(),
                            to = it.value.to.toString(),
                        )
                    }
                availabilityUseCase(Availability(availabilityMap), clinicId).collect { result ->
                    when (result) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            _availabilityStateFlow.update { it.copy(isLoading = false) }
                        }

                        is Resource.Error -> {
                            _availabilityStateFlow.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message,
                                )
                            }
                        }
                    }
                }
            }
        }

        fun getCurrentAvailability(clinicId: Int) {
            viewModelScope.launch {
                _availabilityStateFlow.update { it.copy(isLoading = true) }
                currentAvailabilityUseCase(clinicId).collect { result ->
                    when (result) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            val nonEmptyAvailability =
                                result.data!!.availability.filterValues { dayState ->
                                    dayState.from.isNotEmpty() && dayState.to.isNotEmpty()
                                }

                            if (nonEmptyAvailability.isNotEmpty()) {
                                _availabilityStateFlow.update { availabilityState ->
                                    availabilityState.copy(
                                        dayAvailable =
                                            nonEmptyAvailability.mapValues { mapWithDayState ->
                                                DayStateUi(
                                                    isSwitchOn = mapWithDayState.value.status,
                                                    from = LocalTime.parse(mapWithDayState.value.from),
                                                    to = LocalTime.parse(mapWithDayState.value.to),
                                                )
                                            },
                                        isLoading = false,
                                    )
                                }
                            } else {
                                _availabilityStateFlow.update { it.copy(isLoading = false) }
                            }
                        }

                        is Resource.Error -> {
                            _availabilityStateFlow.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
