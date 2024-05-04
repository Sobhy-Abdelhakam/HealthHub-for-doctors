package dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases

import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.logout()
}
