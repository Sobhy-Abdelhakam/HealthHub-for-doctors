package dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.ClinicRequest
import dev.sobhy.healthhubfordoctors.core.Clinic
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClinicRepository {
    suspend fun addClinic(clinicRequest: ClinicRequest): Flow<Resource<String>>

    suspend fun updateClinic(clinic: ClinicRequest)

    suspend fun getClinics(): Flow<Resource<List<Clinic>>>

    suspend fun getClinic(id: Int): ClinicRequest

    suspend fun deleteClinic(id: Int)
}
