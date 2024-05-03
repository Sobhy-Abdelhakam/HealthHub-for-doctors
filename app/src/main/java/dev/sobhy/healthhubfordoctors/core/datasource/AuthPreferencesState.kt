package dev.sobhy.healthhubfordoctors.core.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferencesState(
    private val context: Context,
) {
    val userToken: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[DataStoreKeys.authToken]
        }

    suspend fun saveUserToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.authToken] = token
        }
    }

    suspend fun clearUserToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(DataStoreKeys.authToken)
        }
    }
}
