package dev.sobhy.healthhubfordoctors.authfeature.domain.repository

import dev.sobhy.healthhubfordoctors.authfeature.data.models.UserDetailsModel
import dev.sobhy.healthhubfordoctors.authfeature.data.remote.RegisterRequest
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest): Flow<Resource<UserDetailsModel>>

    suspend fun login(
        email: String,
        password: String,
    ): Flow<Resource<UserDetailsModel>>

    suspend fun logout(): Flow<Resource<Unit>>

    suspend fun forgetPassword(email: String): Flow<Resource<String>>
}
