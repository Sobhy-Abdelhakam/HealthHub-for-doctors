package dev.sobhy.healthhubfordoctors.authfeature.domain.repository

import com.google.firebase.auth.AuthResult
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String,
    ): Flow<Resource<AuthResult>>
}
