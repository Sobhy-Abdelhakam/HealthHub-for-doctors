package dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.Clinic
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository

class AddClinicUseCase(private val clinicRepository: ClinicRepository) {
    suspend operator fun invoke(clinic: Clinic) = clinicRepository.addClinic(clinic = clinic)
}
