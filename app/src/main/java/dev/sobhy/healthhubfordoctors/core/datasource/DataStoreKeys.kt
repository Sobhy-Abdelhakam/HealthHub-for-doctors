package dev.sobhy.healthhubfordoctors.core.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object DataStoreKeys {
    const val APP_PREFERENCES = "app_preferences"
    val authToken = stringPreferencesKey("auth_token")
    val userId = intPreferencesKey("user_id")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreKeys.APP_PREFERENCES)
