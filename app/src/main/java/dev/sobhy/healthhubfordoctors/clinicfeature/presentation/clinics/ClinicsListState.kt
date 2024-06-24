package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.clinics

import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.ClinicResponse

data class ClinicsListState(
    val clinics: List<ClinicResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
