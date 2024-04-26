package dev.sobhy.healthhubfordoctors.authfeature.data.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.sobhy.healthhubfordoctors.authfeature.data.datasource.datastore.DataStoreKeys.APP_PREFERENCES

object DataStoreKeys {
    const val APP_PREFERENCES = "app_preferences"
    const val USER_TOKEN = "user_token"
    val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_PREFERENCES)
