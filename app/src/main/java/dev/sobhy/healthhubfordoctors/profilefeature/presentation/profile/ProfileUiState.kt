package dev.sobhy.healthhubfordoctors.profilefeature.presentation.profile

import android.net.Uri
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfileUiModel

data class ProfileUiState(
    val image: Uri? = null,
    val doctorInfo: DoctorProfileUiModel = DoctorProfileUiModel(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
