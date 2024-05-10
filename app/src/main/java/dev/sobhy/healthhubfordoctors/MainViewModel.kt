package dev.sobhy.healthhubfordoctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val authPreferences: AuthPreferencesRepository,
    ) : ViewModel() {
        val isLoggedIn =
            authPreferences.getUserToken().stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = runBlocking { authPreferences.getUserToken().first() },
            )
    }
