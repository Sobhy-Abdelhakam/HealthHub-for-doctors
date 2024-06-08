package dev.sobhy.healthhubfordoctors.authfeature.domain.usecase

import dev.sobhy.healthhubfordoctors.authfeature.data.remote.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class RegisterUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        name: String,
        email: String,
        phone: String,
        gender: String,
        dateOfBirth: LocalDate,
        specialization: String,
        professionalTitle: String,
        password: String,
    ): Flow<Resource<String>> {
        return authRepository.register(
            RegisterRequest(
                name = name,
                email = email,
                phone = phone,
                gender = gender,
                dateOfBirth = dateOfBirth,
                specialization = specialization,
                professionalTitle = professionalTitle,
                password = password,
            ),
        )
    }
}