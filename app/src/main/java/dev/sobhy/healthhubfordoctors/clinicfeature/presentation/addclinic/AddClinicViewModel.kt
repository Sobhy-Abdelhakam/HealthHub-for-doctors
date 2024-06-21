package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.authfeature.domain.toClinic
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.AddClinicUseCase
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddClinicViewModel
    @Inject
    constructor(
        private val addClinicUseCase: AddClinicUseCase,
    ) : ViewModel() {
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
                is AddClinicUiEvent.UpdateLocation ->
                    _addClinicState.update {
                        it.copy(latitude = event.latitude, longitude = event.longitude)
                    }
                is AddClinicUiEvent.ExaminationChange ->
                    _addClinicState.update {
                        it.copy(examination = event.examination)
                    }
                is AddClinicUiEvent.FollowUpChange ->
                    _addClinicState.update {
                        it.copy(followUp = event.followUp)
                    }
                AddClinicUiEvent.SaveClinic -> saveClinic()
            }
        }

        private fun saveClinic() {
            viewModelScope.launch(Dispatchers.IO) {
                _addClinicState.update { it.copy(loading = true) }
                val clinic = _addClinicState.value.toClinic()
                Log.d("clinic", clinic.toString())
                addClinicUseCase(clinic).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            _addClinicState.update { it.copy(loading = false, isSubmitting = false, errorMessages = null) }
                        }
                        is Resource.Error -> {
                            _addClinicState.update { it.copy(isSubmitting = false, errorMessages = result.message) }
                        }
                    }
                }
            }
        }

        fun clearErrorMessage() {
            _addClinicState.update { it.copy(errorMessages = null) }
        }
    }
