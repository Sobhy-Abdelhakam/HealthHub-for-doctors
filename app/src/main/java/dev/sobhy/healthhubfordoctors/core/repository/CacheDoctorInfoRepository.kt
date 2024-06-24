package dev.sobhy.healthhubfordoctors.core.repository

import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.toAvailability
import dev.sobhy.healthhubfordoctors.core.toClinic
import dev.sobhy.healthhubfordoctors.core.toDoctor
import dev.sobhy.healthhubfordoctors.core.toEntity
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
            val doctor = response.toDoctor()
            doctorProfileDao.insertDoctorProfile(doctor.toEntity())
            response.clinics.forEach { clinicResponse ->
                val clinic = clinicResponse.toClinic(doctor.uid)
                doctorProfileDao.insertClinic(clinic.toEntity())
                clinicResponse.doctorAvailabilities!!.forEach { availabilityResponse ->
                    val availability = availabilityResponse.toAvailability(clinic.id)
                    doctorProfileDao.insertAvailability(availability.toEntity())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
