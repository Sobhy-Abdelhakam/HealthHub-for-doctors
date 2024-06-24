package dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.ClinicRequest
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.ClinicEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository
import dev.sobhy.healthhubfordoctors.core.Clinic
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
    override suspend fun addClinic(clinicRequest: ClinicRequest): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            val token = authPreferencesRepository.getUserToken().first()
            if (token == null) {
                emit(Resource.Error("User not logged in"))
                return@flow
            }
            val response = clinicService.addClinic(token, clinicRequest)
            if (response.isSuccessful) {
                emit(Resource.Success("Clinic added successfully"))
                val clinic = response.body()!!
                val clinicToStore =
                    ClinicEntity(
                        id = clinic.id,
                        doctorId = clinic.doctorId,
                        name = clinic.name,
                        phone = clinic.phone,
                        examination = clinic.examination,
                        followUp = clinic.followUp,
                        latitude = clinic.latitude,
                        longitude = clinic.longitude,
                        address = clinic.address,
                    )
                doctorInfoDao.insertClinic(clinicToStore)
                return@flow
            } else {
                emit(Resource.Error(response.message()))
            }
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    override suspend fun updateClinic(clinic: ClinicRequest) {
        val token = authPreferencesRepository.getUserToken().first()
        clinicService.updateClinic(token!!, clinic)
    }

    override suspend fun getClinics(): Flow<Resource<List<Clinic>>> {
        val doctorId = authPreferencesRepository.getUserToken().first() ?: ""
        return doctorInfoDao.getClinicsForDoctor(doctorId).map { clinics ->
            if (clinics.isNotEmpty()) {
                Resource.Success(clinics.map { it.toClinic() })
            } else {
                val response = clinicService.getClinics(doctorId)
                val clinicsList =
                    response.body()!!.map {
                        Clinic(
                            id = it.id,
                            doctorId = doctorId,
                            name = it.name,
                            phone = it.phone,
                            examination = it.examination,
                            followUp = it.followUp,
                            latitude = it.latitude,
                            longitude = it.longitude,
                            address = it.address,
                        )
                    }
                val clinicsToStore =
                    response.body()!!.map { clinic ->
                        ClinicEntity(
                            id = clinic.id,
                            doctorId = clinic.doctorId,
                            name = clinic.name,
                            phone = clinic.phone,
                            examination = clinic.examination,
                            followUp = clinic.followUp,
                            latitude = clinic.latitude,
                            longitude = clinic.longitude,
                            address = clinic.address,
                        )
                    }
                clinicsToStore.forEach {
                    doctorInfoDao.insertClinic(it)
                }
                Resource.Success(clinicsList)
            }
        }
    }

    override suspend fun getClinic(id: Int): ClinicRequest {
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
