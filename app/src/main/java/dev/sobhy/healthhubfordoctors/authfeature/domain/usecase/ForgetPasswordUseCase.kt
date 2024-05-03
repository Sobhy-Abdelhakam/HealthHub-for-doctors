package dev.sobhy.healthhubfordoctors.authfeature.domain.usecase

import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository

class ForgetPasswordUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String) = authRepository.forgetPassword(email)
}
