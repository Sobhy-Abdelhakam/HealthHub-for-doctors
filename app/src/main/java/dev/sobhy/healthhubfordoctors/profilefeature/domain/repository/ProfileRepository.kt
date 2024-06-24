package dev.sobhy.healthhubfordoctors.profilefeature.domain.repository

import dev.sobhy.healthhubfordoctors.core.Doctor
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfileInfo(): Flow<Result<Doctor>>
}
