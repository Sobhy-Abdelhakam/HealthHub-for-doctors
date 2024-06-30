package dev.sobhy.healthhubfordoctors.core.repository

import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.AvailabilityEntity
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.model.ClinicEntity
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.DoctorService
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorEntity
import kotlinx.coroutines.flow.first

class CacheDoctorInfoRepository(
    private val doctorService: DoctorService,
    private val doctorProfileDao: DoctorInfoDao,
    private val authPreferences: AuthPreferencesRepository,
) {
    suspend fun getDoctorInfo() {
        val doctorId = authPreferences.getUserId().first()

        try {
            val response = doctorService.getDoctor(doctorId!!)
            val doctor =
                DoctorEntity(
                    id = response.id,
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

            val clinicsToStore =
                response.clinics.map { clinicResponse ->
                    ClinicEntity(
                        id = clinicResponse.id,
                        doctorId = response.id,
                        name = clinicResponse.name,
                        phone = clinicResponse.phone,
                        examination = clinicResponse.examination,
                        followUp = clinicResponse.followUp,
                        latitude = clinicResponse.latitude,
                        longitude = clinicResponse.longitude,
                        address = clinicResponse.address,
                    )
                }

            doctorProfileDao.insertClinics(clinicsToStore)

            response.clinics.forEach { clinicResponse ->
                val availabilitiesToStore =
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

                doctorProfileDao.insertAvailabilities(availabilitiesToStore)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    suspend fun getDoctorInfo() {
//        val doctorId = authPreferences.getUserId().first()
//
//        try {
//            val response = doctorService.getDoctor(doctorId!!)
//            val doctor =
//                DoctorEntity(
//                    id = response.id,
//                    name = response.name,
//                    birthDate = response.birthDate,
//                    phoneNumber = response.phoneNumber,
//                    email = response.email,
//                    gender = response.gender,
//                    imgPath = response.imgPath,
//                    specialty = response.specialty,
//                    profTitle = response.profTitle,
//                    rating = response.rating,
//                )
//            doctorProfileDao.insertDoctorProfile(doctor)
//            response.clinics.forEach { clinicResponse ->
//                val clinic =
//                    ClinicEntity(
//                        id = clinicResponse.id,
//                        doctorId = clinicResponse.doctorId,
//                        name = clinicResponse.name,
//                        phone = clinicResponse.phone,
//                        examination = clinicResponse.examination,
//                        followUp = clinicResponse.followUp,
//                        latitude = clinicResponse.latitude,
//                        longitude = clinicResponse.longitude,
//                        address = clinicResponse.address,
//                    )
//                doctorProfileDao.insertClinic(clinic)
//                clinicResponse.doctorAvailabilities!!.forEach { availabilityResponse ->
//                    val availability =
//                        AvailabilityEntity(
//                            id = availabilityResponse.id,
//                            clinicId = clinicResponse.id,
//                            day = availabilityResponse.day,
//                            startTime = availabilityResponse.startTime,
//                            endTime = availabilityResponse.endTime,
//                            available = availabilityResponse.available,
//                        )
//                    doctorProfileDao.insertAvailability(availability)
//                }
//            }
//
// //            val doctor = response.toDoctor()
// //            doctorProfileDao.insertDoctorProfile(doctor.toEntity())
// //            response.clinics.forEach { clinicResponse ->
// //                val clinic = clinicResponse.toClinic(doctor.uid)
// //                doctorProfileDao.insertClinic(clinic.toEntity())
// //                clinicResponse.doctorAvailabilities!!.forEach { availabilityResponse ->
// //                    val availability = availabilityResponse.toAvailability(clinic.id)
// //                    doctorProfileDao.insertAvailability(availability.toEntity())
// //                }
// //            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
}
