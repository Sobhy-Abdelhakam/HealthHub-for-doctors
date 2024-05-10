package dev.sobhy.healthhubfordoctors.core.repository

import kotlinx.coroutines.flow.Flow

interface AuthPreferencesRepository {
    suspend fun saveUserToken(token: String)

    fun getUserToken(): Flow<String?>

    suspend fun clearUserToken()
}
