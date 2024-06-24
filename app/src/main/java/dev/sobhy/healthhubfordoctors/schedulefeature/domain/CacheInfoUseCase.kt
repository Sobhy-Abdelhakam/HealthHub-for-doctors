package dev.sobhy.healthhubfordoctors.schedulefeature.domain

import dev.sobhy.healthhubfordoctors.core.repository.CacheDoctorInfoRepository

class CacheInfoUseCase(
    private val cacheDoctorInfoRepository: CacheDoctorInfoRepository,
) {
    suspend operator fun invoke() = cacheDoctorInfoRepository.getDoctorProfile()
}
