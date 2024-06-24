package dev.sobhy.healthhubfordoctors.core.repository

import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.AvailabilityEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.ClinicEntity
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorEntity
import kotlinx.coroutines.flow.first

class CacheDoctorInfoRepository(
    private val apiService: ApiService,
    private val doctorProfileDao: DoctorInfoDao,
    private val authPreferences: AuthPreferencesRepository,
) {
    suspend fun getDoctorInfo() {
        val doctorId = authPreferences.getUserToken().first()

        try {
            val response = apiService.getDoctor(doctorId!!)
            val doctor =
                DoctorEntity(
                    uid = response.uid,
                    name = response.name,
                    birthDate = response.birthDate,
                    phoneNumber = response.phoneNumber,
                    email = response.email,
                    gender = response.gender,
                    imgPath = response.imgPath,
                    specialty = response.specialty,
                    profTitle = response.profTitle,
                    rating = response.rating,
                )
            doctorProfileDao.insertDoctorProfile(doctor)
            response.clinics.forEach { clinicResponse ->
                val clinic =
                    ClinicEntity(
                        id = clinicResponse.id,
                        doctorId = clinicResponse.doctorId,
                        name = clinicResponse.name,
                        phone = clinicResponse.phone,
                        examination = clinicResponse.examination,
                        followUp = clinicResponse.followUp,
                        latitude = clinicResponse.latitude,
                        longitude = clinicResponse.longitude,
                        address = clinicResponse.address,
                    )
                doctorProfileDao.insertClinic(clinic)
                clinicResponse.doctorAvailabilities!!.forEach { availabilityResponse ->
                    val availability =
                        AvailabilityEntity(
                            id = availabilityResponse.id,
                            clinicId = clinicResponse.id,
                            day = availabilityResponse.day,
                            startTime = availabilityResponse.startTime,
                            endTime = availabilityResponse.endTime,
                            available = availabilityResponse.available,
                        )
                    doctorProfileDao.insertAvailability(availability)
                }
            }

//            val doctor = response.toDoctor()
//            doctorProfileDao.insertDoctorProfile(doctor.toEntity())
//            response.clinics.forEach { clinicResponse ->
//                val clinic = clinicResponse.toClinic(doctor.uid)
//                doctorProfileDao.insertClinic(clinic.toEntity())
//                clinicResponse.doctorAvailabilities!!.forEach { availabilityResponse ->
//                    val availability = availabilityResponse.toAvailability(clinic.id)
//                    doctorProfileDao.insertAvailability(availability.toEntity())
//                }
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
