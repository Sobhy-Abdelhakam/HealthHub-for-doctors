package dev.sobhy.healthhubfordoctors.core.repository

import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import kotlinx.coroutines.flow.first

class CacheDoctorInfoRepository(
    private val apiService: ApiService,
    private val doctorProfileDao: DoctorInfoDao,
    private val authPreferences: AuthPreferencesRepository,
) {
    suspend fun getDoctorProfile() {
        val doctorId = authPreferences.getUserToken().first()

        try {
            val freshProfile = apiService.getDoctor(doctorId!!)
            doctorProfileDao.insertDoctorProfile(freshProfile)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
