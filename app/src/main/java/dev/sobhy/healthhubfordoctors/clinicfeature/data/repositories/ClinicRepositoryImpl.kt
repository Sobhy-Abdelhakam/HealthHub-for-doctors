package dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Clinic
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class ClinicRepositoryImpl(
    private val clinicService: ApiService,
    private val authPreferencesRepository: AuthPreferencesRepository,
) : ClinicRepository {
    override suspend fun addClinic(clinic: Clinic): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            val token = authPreferencesRepository.getUserToken().first()
            if (token == null) {
                emit(Resource.Error("User not logged in"))
                return@flow
            }
            val response = clinicService.addClinic(token, clinic)
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

    override suspend fun getClinics(): Flow<Resource<List<Clinic>>> {
        return flow {
            emit(Resource.Loading())
            val token = authPreferencesRepository.getUserToken().first()
            if (token == null) {
                emit(Resource.Error("User not logged in"))
                return@flow
            }
            val response = clinicService.getClinics(token)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
                return@flow
            } else {
                emit(Resource.Error(response.message()))
            }
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun getClinic(id: Int): Clinic {
        return clinicService.getClinic(id)
    }

    override suspend fun deleteClinic(id: Int) {
        clinicService.deleteClinic(id)
    }
}
