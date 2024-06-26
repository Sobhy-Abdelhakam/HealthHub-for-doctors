package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.clinics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases.GetAllClinicsUseCase
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClinicsViewModel
    @Inject
    constructor(private val clinicsUseCase: GetAllClinicsUseCase) : ViewModel() {
        val clinics: MutableList<String> = mutableListOf()
        private val _clinicsState = MutableStateFlow(ClinicsListState())
        val clinicsState = _clinicsState.asStateFlow()

        init {
            getClinics()
        }

        fun getClinics() {
            viewModelScope.launch(Dispatchers.IO) {
                clinicsUseCase()
                    .collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _clinicsState.value = ClinicsListState(isLoading = true)
                            }
                            is Resource.Error -> {
                                _clinicsState.value = ClinicsListState(error = result.message)
                            }
                            is Resource.Success -> {
                                _clinicsState.value = ClinicsListState(clinics = result.data ?: emptyList())
                            }
                        }
                    }
            }
        }
    }
