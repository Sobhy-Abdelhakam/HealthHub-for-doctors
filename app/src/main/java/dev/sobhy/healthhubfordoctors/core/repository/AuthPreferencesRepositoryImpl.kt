package dev.sobhy.healthhubfordoctors.core.repository

import dev.sobhy.healthhubfordoctors.core.datasource.AuthPreferencesState
import kotlinx.coroutines.flow.Flow

class AuthPreferencesRepositoryImpl(
    private val authPreferencesDataStore: AuthPreferencesState,
) : AuthPreferencesRepository {
    override suspend fun saveLoginState(isLoggedIn: Boolean) {
        authPreferencesDataStore.setIsUserLoggedIn(isLoggedIn)
    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
        return authPreferencesDataStore.isUserLoggedIn
    }

    override suspend fun saveUserToken(token: String) {
        authPreferencesDataStore.saveUserToken(token)
    }

    override suspend fun getUserToken(): Flow<String?> {
        return authPreferencesDataStore.userToken
    }

    override suspend fun clearUserToken() {
        authPreferencesDataStore.clearUserToken()
    }
}
