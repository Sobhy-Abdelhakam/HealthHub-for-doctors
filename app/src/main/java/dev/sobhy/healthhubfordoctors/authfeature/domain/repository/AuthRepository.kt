package dev.sobhy.healthhubfordoctors.authfeature.domain.repository

import dev.sobhy.healthhubfordoctors.authfeature.data.request.RegisterRequest
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest): Flow<Resource<String>>

    suspend fun login(
        email: String,
        password: String,
    ): Flow<Resource<String>>

    suspend fun logout(): Flow<Resource<Unit>>

    suspend fun forgetPassword(email: String): Flow<Resource<String>>

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
    ): Flow<Resource<String>>
}
