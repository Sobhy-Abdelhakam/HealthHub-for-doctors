package dev.sobhy.healthhubfordoctors.profilefeature.data.repository

import dev.sobhy.healthhubfordoctors.authfeature.domain.model.DoctorRequest
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.profilefeature.data.local.DoctorProfileDao
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfile
import dev.sobhy.healthhubfordoctors.profilefeature.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl(
    private val apiService: ApiService,
    private val doctorProfileDao: DoctorProfileDao,
    private val authPreferences: AuthPreferencesRepository,
) : ProfileRepository {
    override suspend fun getDoctorProfile(): Flow<DoctorProfile> {
        return flow {
            val doctorId = authPreferences.getUserToken().first()
            val cachedProfile = doctorProfileDao.getDoctorProfile(doctorId!!)
            cachedProfile?.let {
                emit(it)
            }
            try {
                val freshProfile = apiService.getDoctor(doctorId)
                doctorProfileDao.insertDoctorProfile(freshProfile)
                emit(freshProfile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun updateDoctorProfile(doctorRequest: DoctorRequest) {
        TODO("Not yet implemented")
    }
}
