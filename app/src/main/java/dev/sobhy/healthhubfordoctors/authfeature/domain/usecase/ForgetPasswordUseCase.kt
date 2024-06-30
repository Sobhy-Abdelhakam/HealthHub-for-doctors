package dev.sobhy.healthhubfordoctors.authfeature.domain.usecase

import dev.sobhy.healthhubfordoctors.authfeature.data.request.ResetPassRequest
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository

class ForgetPasswordUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String) = authRepository.sendOtp(email)

    suspend fun submitNewPassword(
        email: String,
        otp: String,
        newPassword: String,
    ) = authRepository.resetPassword(ResetPassRequest(email, otp, newPassword))
}
