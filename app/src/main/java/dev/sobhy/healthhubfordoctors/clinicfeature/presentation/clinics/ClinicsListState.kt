package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.clinics

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Clinic

data class ClinicsListState(
    val clinics: List<Clinic> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
