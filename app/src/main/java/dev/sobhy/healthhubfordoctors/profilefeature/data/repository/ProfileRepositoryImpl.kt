package dev.sobhy.healthhubfordoctors.profilefeature.data.repository

import dev.sobhy.healthhubfordoctors.core.data.local.DoctorInfoDao
import dev.sobhy.healthhubfordoctors.core.data.remote.ApiService
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfileUiModel
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.toDoctorProfileUiModel
import dev.sobhy.healthhubfordoctors.profilefeature.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(
    private val doctorInfoDao: DoctorInfoDao,
    private val apiService: ApiService,
    private val preferencesRepository: AuthPreferencesRepository,
) : ProfileRepository {
    override suspend fun getProfileInfo(): Flow<Result<DoctorProfileUiModel>> {
        val userId = preferencesRepository.getUserToken().first()
        return doctorInfoDao.getProfileInfo(userId!!).map { userEntity ->
            if (userEntity != null) {
                Result.success(userEntity)
            } else {
                try {
                    val response = apiService.getDoctor(userId)
                    val user = response.toDoctorProfileUiModel()
                    doctorInfoDao.insertDoctorProfile(response)
                    Result.success(user)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        }.catch {
            emit(Result.failure(it))
        }
    }
}
