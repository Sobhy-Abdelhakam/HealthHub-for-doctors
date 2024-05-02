package dev.sobhy.healthhubfordoctors

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.core.repository.AuthPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val authPreferences: AuthPreferencesRepository,
    ) : ViewModel() {
        private val _isLoggedIn = MutableStateFlow<String?>(null)
        val isLoggedIn = _isLoggedIn.asStateFlow()

        init {
            viewModelScope.launch(Dispatchers.Main) {
                Log.d("is logged in: ", isLoggedIn.toString())

                authPreferences.getUserToken().collectLatest {
                    Log.d("from preferences: ", it.toString())
                    _isLoggedIn.value = it
                }
            }
        }
    }
