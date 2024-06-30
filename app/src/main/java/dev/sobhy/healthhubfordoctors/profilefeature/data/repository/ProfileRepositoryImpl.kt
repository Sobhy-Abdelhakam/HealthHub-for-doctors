package dev.sobhy.healthhubfordoctors.profilefeature.data.repository

import dev.sobhy.healthhubfordoctors.core.Doctor
import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.DoctorService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.core.toDoctor
import dev.sobhy.healthhubfordoctors.core.toEntity
import dev.sobhy.healthhubfordoctors.profilefeature.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(
    private val doctorInfoDao: DoctorInfoDao,
    private val doctorService: DoctorService,
    private val preferencesRepository: AuthPreferencesRepository,
) : ProfileRepository {
    override suspend fun getProfileInfo(): Flow<Result<Doctor>> {
        val userId = preferencesRepository.getUserId().first() ?: 1
        return doctorInfoDao.getAllDoctorInfo(userId).map { doctorEntity ->
            if (doctorEntity != null) {
                Result.success(doctorEntity.toDoctor())
            } else {
                try {
                    val response = doctorService.getDoctor(userId)
                    val doctor = response.toDoctor()
                    doctorInfoDao.insertDoctorProfile(doctor.toEntity())
                    Result.success(doctor)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        }.catch {
            emit(Result.failure(it))
        }
    }
}
