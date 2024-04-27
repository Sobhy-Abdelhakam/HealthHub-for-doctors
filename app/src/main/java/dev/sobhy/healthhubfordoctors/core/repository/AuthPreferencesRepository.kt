package dev.sobhy.healthhubfordoctors.core.repository

import kotlinx.coroutines.flow.Flow

interface AuthPreferencesRepository {
    suspend fun saveLoginState(isLoggedIn: Boolean)

    suspend fun isLoggedIn(): Flow<Boolean>
}
