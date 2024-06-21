package dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Clinic
import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.GetClinicResponse
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClinicRepository {
    suspend fun addClinic(clinic: Clinic): Flow<Resource<String>>

    suspend fun updateClinic(clinic: Clinic)

    suspend fun getClinics(): Flow<Resource<List<GetClinicResponse>>>

    suspend fun getClinic(id: Int): Clinic

    suspend fun deleteClinic(id: Int)
}
