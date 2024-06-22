package dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Availability
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.AvailabilityRepository

class AvailabilityUseCase(private val availabilityRepository: AvailabilityRepository) {
    suspend operator fun invoke(
        availability: Availability,
        clinicId: Int,
    ) = availabilityRepository.setAvailability(availability, clinicId)
}
