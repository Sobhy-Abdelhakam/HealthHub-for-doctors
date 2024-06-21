package dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Clinic
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ClinicRepositoryImpl(private val clinicService: ApiService) : ClinicRepository {
    override suspend fun addClinic(clinic: Clinic): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            val response = clinicService.addClinic(clinic)
            if (response.isSuccessful) {
                emit(Resource.Success("Clinic added successfully"))
                return@flow
            } else {
                emit(Resource.Error(response.message()))
            }
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun updateClinic(
        id: Int,
        clinic: Clinic,
    ) {
        clinicService.updateClinic(id, clinic)
    }

    override suspend fun getClinics(): List<Clinic> {
        return clinicService.getClinics()
    }

    override suspend fun getClinic(id: Int): Clinic {
        return clinicService.getClinic(id)
    }

    override suspend fun deleteClinic(id: Int) {
        clinicService.deleteClinic(id)
    }
}
