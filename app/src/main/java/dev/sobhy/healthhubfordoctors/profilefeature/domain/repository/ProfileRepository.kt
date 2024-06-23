package dev.sobhy.healthhubfordoctors.profilefeature.domain.repository

import dev.sobhy.healthhubfordoctors.authfeature.domain.model.DoctorRequest
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getDoctorProfile(): Flow<DoctorProfile>

    suspend fun updateDoctorProfile(doctorRequest: DoctorRequest)
}
