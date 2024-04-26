package dev.sobhy.healthhubfordoctors.authfeature.data.repository

import kotlinx.coroutines.flow.Flow

interface AuthPreferencesRepository {
    suspend fun saveLoginState(isLoggedIn: Boolean)

    suspend fun isLoggedIn(): Flow<Boolean>
}
