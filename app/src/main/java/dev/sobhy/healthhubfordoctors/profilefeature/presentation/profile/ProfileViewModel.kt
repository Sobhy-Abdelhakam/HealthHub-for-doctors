package dev.sobhy.healthhubfordoctors.profilefeature.presentation.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.ChangeProfileImage ->
                _uiState.update {
                    it.copy(image = event.image)
                }
        }
    }
}
