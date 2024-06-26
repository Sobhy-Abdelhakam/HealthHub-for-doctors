package dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Availability
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AvailabilityRepository {
    suspend fun setAvailability(
        availability: Availability,
        clinicId: Int,
    ): Flow<Resource<String>>

    suspend fun getAvailability(clinicId: Int): Flow<Resource<Availability>>
}
