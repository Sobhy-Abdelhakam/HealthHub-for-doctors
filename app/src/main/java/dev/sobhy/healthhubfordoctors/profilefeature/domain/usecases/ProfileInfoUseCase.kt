package dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases

import dev.sobhy.healthhubfordoctors.profilefeature.domain.repository.ProfileRepository

class ProfileInfoUseCase(private val profileInfoRepository: ProfileRepository) {
    suspend operator fun invoke() = profileInfoRepository.getProfileInfo()
}
