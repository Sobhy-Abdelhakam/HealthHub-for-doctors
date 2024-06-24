package dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Clinic
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.core.toClinic
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ClinicRepositoryImpl(
    private val clinicService: ApiService,
    private val doctorInfoDao: DoctorInfoDao,
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

    override suspend fun updateClinic(clinic: Clinic) {
        val token = authPreferencesRepository.getUserToken().first()
        clinicService.updateClinic(token!!, clinic)
    }

    override suspend fun getClinics(): Flow<Resource<List<dev.sobhy.healthhubfordoctors.core.Clinic>>> {
        val doctorId = authPreferencesRepository.getUserToken().first() ?: ""
        return doctorInfoDao.getClinicsForDoctor(doctorId).map { clinics ->
            Resource.Success(clinics.map { it.toClinic() })
//            if (clinics.isNotEmpty()) {
//                Resource.Success(clinics.map { it.toClinic() })
//            } else {
//                val response = clinicService.getClinics(doctorId)
//                val clinics = response.body()!!.map {
//                    it.toClinic(doctorId)
//                }
//                doctorInfoDao.insertClinic(clinics)
//                Resource.Success(clinics)
//            }
        }
    }

    override suspend fun getClinic(id: Int): Clinic {
        TODO("Not yet implemented")
    }

    override suspend fun deleteClinic(id: Int) {
        TODO("Not yet implemented")
    }
//        return flow {
//            emit(Resource.Loading())
//            val token = authPreferencesRepository.getUserToken().first()
//            if (token == null) {
//                emit(Resource.Error("User not logged in"))
//                return@flow
//            }
//            val clinics = doctorInfoDao.getClinicsForDoctor(token)
//            val response = clinicService.getClinics(token)
//            if (response.isSuccessful) {
//                emit(Resource.Success(response.body()!!))
//                return@flow
//            } else {
//                emit(Resource.Error(response.message()))
//            }
//        }.catch {
//            emit(Resource.Error(it.message ?: "An error occurred"))
//        }
}
