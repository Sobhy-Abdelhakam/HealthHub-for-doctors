package dev.sobhy.healthhubfordoctors.profilefeature.domain.repository

import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfileUiModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfileInfo(): Flow<Result<DoctorProfileUiModel>>
}
