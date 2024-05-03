package dev.sobhy.healthhubfordoctors.authfeature.domain.usecase

import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ) = authRepository.login(email, password)
}
