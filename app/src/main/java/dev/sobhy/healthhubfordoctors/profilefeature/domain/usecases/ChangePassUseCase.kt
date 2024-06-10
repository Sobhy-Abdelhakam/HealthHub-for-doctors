package dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases

import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

class ChangePassUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String,
    ): Flow<Resource<String>> {
        return authRepository.changePassword(currentPassword, newPassword)
    }
}
