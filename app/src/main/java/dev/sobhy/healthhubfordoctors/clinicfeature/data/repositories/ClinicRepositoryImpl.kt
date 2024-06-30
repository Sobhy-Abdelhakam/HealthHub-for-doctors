package dev.sobhy.healthhubfordoctors.clinicfeature.data.repositories

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.ClinicRequest
import dev.sobhy.healthhubfordoctors.clinicfeature.data.remote.ClinicService
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.AvailabilityEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.ClinicEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository
import dev.sobhy.healthhubfordoctors.core.Availability
import dev.sobhy.healthhubfordoctors.core.Clinic
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.core.toAvailability
import dev.sobhy.healthhubfordoctors.core.toClinic
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ClinicRepositoryImpl(
    private val clinicService: ClinicService,
    private val doctorInfoDao: DoctorInfoDao,
    private val authPreferencesRepository: AuthPreferencesRepository,
) : ClinicRepository {
    override suspend fun addClinic(clinicRequest: ClinicRequest): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            val token = authPreferencesRepository.getUserId().first()
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
        val token = authPreferencesRepository.getUserId().first()
        clinicService.updateClinic(token!!, clinic)
    }

    override suspend fun getClinics(): Flow<Resource<List<Clinic>>> {
        val doctorId = authPreferencesRepository.getUserId().first() ?: 1
        return doctorInfoDao.getClinicsWithAvailabilities(doctorId).map { clinicsWithAvailabilities ->
            if (clinicsWithAvailabilities.isNotEmpty()) {
                val clinics =
                    clinicsWithAvailabilities.map { clinicWithAvailabilities ->
                        clinicWithAvailabilities.clinic.toClinic(
                            clinicWithAvailabilities.availabilities.map { it.toAvailability() },
                        )
                    }
                Resource.Success(clinics)
            } else {
                val response = clinicService.getClinics(doctorId)
                val responseBody = response.body()

                if (responseBody != null) {
                    val clinicsToStore =
                        responseBody.map { clinicResponse ->
                            ClinicEntity(
                                id = clinicResponse.id,
                                doctorId = doctorId,
                                name = clinicResponse.name,
                                phone = clinicResponse.phone,
                                examination = clinicResponse.examination,
                                followUp = clinicResponse.followUp,
                                latitude = clinicResponse.latitude,
                                longitude = clinicResponse.longitude,
                                address = clinicResponse.address,
                            )
                        }

                    val availabilitiesToStore =
                        responseBody.flatMap { clinicResponse ->
                            clinicResponse.doctorAvailabilities?.map { availabilityResponse ->
                                AvailabilityEntity(
                                    id = availabilityResponse.id,
                                    clinicId = clinicResponse.id,
                                    day = availabilityResponse.day,
                                    startTime = availabilityResponse.startTime,
                                    endTime = availabilityResponse.endTime,
                                    available = availabilityResponse.available,
                                )
                            } ?: emptyList()
                        }

                    // Insert clinics first to ensure parent records exist
                    doctorInfoDao.insertClinics(clinicsToStore)

                    // Insert availabilities after clinics
                    doctorInfoDao.insertAvailabilities(availabilitiesToStore)

                    val clinicsList =
                        responseBody.map { clinicResponse ->
                            Clinic(
                                id = clinicResponse.id,
                                doctorId = doctorId,
                                name = clinicResponse.name,
                                phone = clinicResponse.phone,
                                examination = clinicResponse.examination,
                                followUp = clinicResponse.followUp,
                                latitude = clinicResponse.latitude,
                                longitude = clinicResponse.longitude,
                                address = clinicResponse.address,
                                availabilities =
                                    clinicResponse.doctorAvailabilities?.map {
                                        Availability(
                                            id = it.id,
                                            clinicId = clinicResponse.id,
                                            day = it.day,
                                            startTime = it.startTime,
                                            endTime = it.endTime,
                                            available = it.available,
                                        )
                                    } ?: emptyList(),
                            )
                        }

                    Resource.Success(clinicsList)
                } else {
                    Resource.Error("Failed to fetch clinics")
                }
            }
        }
    }

//    override suspend fun getClinics(): Flow<Resource<List<Clinic>>> {
//        val doctorId = authPreferencesRepository.getUserId().first() ?: 1
//        return doctorInfoDao.getClinicsWithAvailabilities(doctorId).map { clinicsWithAvailabilities ->
//            if (clinicsWithAvailabilities.isNotEmpty()) {
//                val clinics =
//                    clinicsWithAvailabilities.map { clinicWithAvailabilities ->
//                        clinicWithAvailabilities.clinic.toClinic(
//                            clinicWithAvailabilities.availabilities.map { it.toAvailability() },
//                        )
//                    }
//                Resource.Success(clinics)
//            } else {
//                val response = clinicService.getClinics(doctorId)
//                val responseBody = response.body()
//
//                if (responseBody != null) {
//                    val clinicsList =
//                        responseBody.map { clinicResponse ->
//                            Clinic(
//                                id = clinicResponse.id,
//                                doctorId = doctorId,
//                                name = clinicResponse.name,
//                                phone = clinicResponse.phone,
//                                examination = clinicResponse.examination,
//                                followUp = clinicResponse.followUp,
//                                latitude = clinicResponse.latitude,
//                                longitude = clinicResponse.longitude,
//                                address = clinicResponse.address,
//                                availabilities =
//                                    clinicResponse.doctorAvailabilities?.map {
//                                        Availability(
//                                            id = it.id,
//                                            clinicId = clinicResponse.id,
//                                            day = it.day,
//                                            startTime = it.startTime,
//                                            endTime = it.endTime,
//                                            available = it.available,
//                                        )
//                                    } ?: emptyList(),
//                            )
//                        }
//
//                    val clinicsToStore =
//                        responseBody.map { clinicResponse ->
//                            ClinicEntity(
//                                id = clinicResponse.id,
//                                doctorId = doctorId,
//                                name = clinicResponse.name,
//                                phone = clinicResponse.phone,
//                                examination = clinicResponse.examination,
//                                followUp = clinicResponse.followUp,
//                                latitude = clinicResponse.latitude,
//                                longitude = clinicResponse.longitude,
//                                address = clinicResponse.address,
//                            )
//                        }
//
//                    val availabilitiesToStore =
//                        responseBody.flatMap { clinicResponse ->
//                            clinicResponse.doctorAvailabilities?.map { availabilityResponse ->
//                                AvailabilityEntity(
//                                    id = availabilityResponse.id,
//                                    clinicId = clinicResponse.id,
//                                    day = availabilityResponse.day,
//                                    startTime = availabilityResponse.startTime,
//                                    endTime = availabilityResponse.endTime,
//                                    available = availabilityResponse.available,
//                                )
//                            } ?: emptyList()
//                        }
//
//                    clinicsToStore.forEach {
//                        doctorInfoDao.insertClinic(it)
//                    }
//
//                    availabilitiesToStore.forEach {
//                        doctorInfoDao.insertAvailability(it)
//                    }
//
//                    Resource.Success(clinicsList)
//                } else {
//                    Resource.Error("Failed to fetch clinics")
//                }
//            }
//        }
//    }

//    override suspend fun getClinics(): Flow<Resource<List<Clinic>>> {
//        val doctorId = authPreferencesRepository.getUserId().first() ?: 1
//        return doctorInfoDao.getClinicsWithAvailabilities(doctorId).map { clinicsWithAvailabilities ->
//            if (clinicsWithAvailabilities.isNotEmpty()) {
//                val clinics =
//                    clinicsWithAvailabilities.map { clinicWithAvailabilities ->
//                        clinicWithAvailabilities.clinic.toClinic(
//                            clinicWithAvailabilities.availabilities.map { it.toAvailability() },
//                        )
//                    }
//                Resource.Success(clinics)
//            } else {
//                val response = clinicService.getClinics(doctorId)
//                val clinicsList =
//                    response.body()!!.map { clinicResponse ->
//                        Clinic(
//                            id = clinicResponse.id,
//                            doctorId = doctorId,
//                            name = clinicResponse.name,
//                            phone = clinicResponse.phone,
//                            examination = clinicResponse.examination,
//                            followUp = clinicResponse.followUp,
//                            latitude = clinicResponse.latitude,
//                            longitude = clinicResponse.longitude,
//                            address = clinicResponse.address,
//                            availabilities =
//                                clinicResponse.doctorAvailabilities!!.map {
//                                    Availability(
//                                        id = it.id,
//                                        clinicId = clinicResponse.id,
//                                        day = it.day,
//                                        startTime = it.startTime,
//                                        endTime = it.endTime,
//                                        available = it.available,
//                                    )
//                                },
//                        )
//                    }
//
//                val clinicsToStore =
//                    response.body()!!.map { clinicResponse ->
//                        ClinicEntity(
//                            id = clinicResponse.id,
//                            doctorId = doctorId,
//                            name = clinicResponse.name,
//                            phone = clinicResponse.phone,
//                            examination = clinicResponse.examination,
//                            followUp = clinicResponse.followUp,
//                            latitude = clinicResponse.latitude,
//                            longitude = clinicResponse.longitude,
//                            address = clinicResponse.address,
//                        )
//                    }
//
//                val availabilitiesToStore =
//                    response.body()!!.flatMap { clinicResponse ->
//                        clinicResponse.doctorAvailabilities!!.map { availabilityResponse ->
//                            AvailabilityEntity(
//                                id = availabilityResponse.id,
//                                clinicId = clinicResponse.id,
//                                day = availabilityResponse.day,
//                                startTime = availabilityResponse.startTime,
//                                endTime = availabilityResponse.endTime,
//                                available = availabilityResponse.available,
//                            )
//                        }
//                    }
//
//                clinicsToStore.forEach {
//                    doctorInfoDao.insertClinic(it)
//                }
//
//                availabilitiesToStore.forEach {
//                    doctorInfoDao.insertAvailability(it)
//                }
//
//                Resource.Success(clinicsList)
//            }
//        }
//    }

    override suspend fun getClinic(id: Int): ClinicRequest {
        TODO("Not yet implemented")
    }

    override suspend fun deleteClinic(id: Int) {
        TODO("Not yet implemented")
    }
}
