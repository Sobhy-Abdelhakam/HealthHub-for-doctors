package dev.sobhy.healthhubfordoctors.profilefeature.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.core.util.Resource
import dev.sobhy.healthhubfordoctors.profilefeature.domain.model.DoctorProfileUiModel
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.LogoutUseCase
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.ProfileInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val logoutUseCase: LogoutUseCase,
        private val profileInfoUseCase: ProfileInfoUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ProfileUiState())
        val uiState: StateFlow<ProfileUiState> = _uiState

        var isLoggedOut: Boolean by mutableStateOf(false)

        init {
            getProfileInfo()
        }

        fun onEvent(event: ProfileUiEvent) {
            when (event) {
                is ProfileUiEvent.ChangeProfileImage ->
                    _uiState.update {
                        it.copy(image = event.image)
                    }

                ProfileUiEvent.Logout -> logOut()
            }
        }

        private fun getProfileInfo() {
            viewModelScope.launch {
                profileInfoUseCase()
                    .onStart {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    .collect { result ->
                        if (result.isSuccess) {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    doctorInfo =
                                        DoctorProfileUiModel(
                                            result.getOrNull()!!.name,
                                            result.getOrNull()!!.specialty,
                                            result.getOrNull()!!.imgPath,
                                            result.getOrNull()!!.rating,
                                        ),
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.exceptionOrNull()?.message,
                                )
                            }
                        }
                    }
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
