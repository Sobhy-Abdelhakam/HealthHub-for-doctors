package dev.sobhy.healthhubfordoctors.core.repository

import kotlinx.coroutines.flow.Flow

interface AuthPreferencesRepository {
    suspend fun saveUserToken(token: String)

    suspend fun saveUserId(id: Int)

    fun getUserId(): Flow<Int?>

    fun getUserToken(): Flow<String?>

    suspend fun clearUserToken()
}
