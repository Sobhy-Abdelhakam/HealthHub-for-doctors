package dev.sobhy.healthhubfordoctors.authfeature.domain.repository

import dev.sobhy.healthhubfordoctors.authfeature.data.remote.RegisterRequest
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun register(registerRequest: RegisterRequest): Flow<Resource<Unit>>

    fun login(
        email: String,
        password: String,
    ): Flow<Resource<Unit>>

    fun logout(): Flow<Resource<Unit>>

    fun forgetPassword(email: String): Flow<Resource<Unit>>
}
