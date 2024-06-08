package dev.sobhy.healthhubfordoctors.profilefeature.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.core.util.Resource
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val logoutUseCase: LogoutUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ProfileUiState())
        val uiState: StateFlow<ProfileUiState> = _uiState

        var isLoggedOut: Boolean by mutableStateOf(false)

        fun onEvent(event: ProfileUiEvent) {
            when (event) {
                is ProfileUiEvent.ChangeProfileImage ->
                    _uiState.update {
                        it.copy(image = event.image)
                    }

                ProfileUiEvent.Logout -> logOut()
            }
        }

        private fun logOut() {
            viewModelScope.launch {
                logoutUseCase.invoke().collect {
                    when (it) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> isLoggedOut = true
                    }
                }
            }
        }
    }