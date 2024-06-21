package dev.sobhy.healthhubfordoctors.clinicfeature.domain.usecases

import dev.sobhy.healthhubfordoctors.clinicfeature.domain.repository.ClinicRepository

class GetAllClinicsUseCase(private val clinicRepository: ClinicRepository) {
    suspend operator fun invoke() = clinicRepository.getClinics()
}
