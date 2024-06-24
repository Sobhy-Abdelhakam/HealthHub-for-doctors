package dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.ClinicRequest
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClinicRepository {
    suspend fun addClinic(clinic: ClinicRequest): Flow<Resource<String>>

    suspend fun updateClinic(clinic: ClinicRequest)

    suspend fun getClinics(): Flow<Resource<List<dev.sobhy.healthhubfordoctors.core.Clinic>>>

    suspend fun getClinic(id: Int): ClinicRequest

    suspend fun deleteClinic(id: Int)
}
