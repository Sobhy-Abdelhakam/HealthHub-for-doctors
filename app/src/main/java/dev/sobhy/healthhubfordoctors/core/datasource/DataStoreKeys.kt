package dev.sobhy.healthhubfordoctors.core.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object DataStoreKeys {
    const val APP_PREFERENCES = "app_preferences"
    val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
    val authToken = stringPreferencesKey("auth_token")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreKeys.APP_PREFERENCES)
