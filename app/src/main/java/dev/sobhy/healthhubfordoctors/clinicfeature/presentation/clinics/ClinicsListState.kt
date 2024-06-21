package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.clinics

import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.GetClinicResponse

data class ClinicsListState(
    val clinics: List<GetClinicResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
