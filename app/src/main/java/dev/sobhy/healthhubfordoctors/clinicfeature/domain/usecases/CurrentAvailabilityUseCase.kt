package dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases

import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.AvailabilityRepository

class CurrentAvailabilityUseCase(private val availabilityRepository: AvailabilityRepository) {
    suspend operator fun invoke(clinicId: Int) = availabilityRepository.getAvailability(clinicId)
}
