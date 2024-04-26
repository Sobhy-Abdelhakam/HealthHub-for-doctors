package dev.sobhy.healthhubfordoctors.authfeature.data.repository

import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.datastore.AuthPreferencesState
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
}
