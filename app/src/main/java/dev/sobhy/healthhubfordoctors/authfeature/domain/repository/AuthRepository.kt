package dev.sobhy.healthhubfordoctors.authfeature.domain.repository

import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(
        email: String,
        password: String,
    ): Flow<Resource<Unit>>
}
