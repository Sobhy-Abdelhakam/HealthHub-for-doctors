package dev.sobhy.healthhubfordoctors.authfeature.domain.use_case

import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository

class ForgetPasswordUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(email: String) = authRepository.forgetPassword(email)
}
