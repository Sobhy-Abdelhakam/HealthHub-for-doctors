package dev.sobhy.healthhubfordoctors.authfeature.domain.use_case

import dev.sobhy.healthhubfordoctors.authfeature.data.remote.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(
        name: String,
        email: String,
        phone: String,
        gender: String,
        dateOfBirth: String,
        specialization: String,
        professionalTitle: String,
        password: String,
    ): Flow<Resource<Unit>> {
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
