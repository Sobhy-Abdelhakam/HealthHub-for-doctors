package dev.sobhy.healthhubfordoctors.authfeature.domain.use_case

import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ) = authRepository.login(email, password)
}
