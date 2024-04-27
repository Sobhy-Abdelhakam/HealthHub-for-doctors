package dev.sobhy.healthhubfordoctors.core.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferencesState(
    private val context: Context,
) {
    val isUserLoggedIn: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[DataStoreKeys.IS_USER_LOGGED_IN] ?: false
        }

    suspend fun setIsUserLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.IS_USER_LOGGED_IN] = isLoggedIn
        }
    }
}
