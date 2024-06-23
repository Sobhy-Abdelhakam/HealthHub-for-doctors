package dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases

import dev.sobhy.healthhubfordoctors.profilefeature.domain.repository.ProfileRepository

class GetDoctorProfileUseCase(
    private val doctorRepository: ProfileRepository,
) {
    suspend operator fun invoke() = doctorRepository.getDoctorProfile()
}
