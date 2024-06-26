package dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases

import dev.sobhy.healthhubfordoctors.clinicfeature.data.model.ClinicRequest
import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository

class AddClinicUseCase(private val clinicRepository: ClinicRepository) {
    suspend operator fun invoke(clinic: ClinicRequest) = clinicRepository.addClinic(clinicRequest = clinic)
}
