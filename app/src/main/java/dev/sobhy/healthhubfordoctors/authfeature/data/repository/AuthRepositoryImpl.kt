package dev.sobhy.healthhubfordoctors.authfeature.data.repository

import dev.sobhy.healthhubfordoctors.authfeature.data.remote.RegisterRequest
import dev.sobhy.healthhubfordoctors.authfeature.domain.repository.AuthRepository
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl : AuthRepository {
    override fun register(registerRequest: RegisterRequest): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun login(
        email: String,
        password: String,
    ): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun logout(): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }
}
