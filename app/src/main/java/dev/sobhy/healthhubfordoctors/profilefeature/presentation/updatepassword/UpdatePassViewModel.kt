package dev.sobhy.healthhubfordoctors.profilefeature.presentation.updatepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.ValidatePassword
import dev.sobhy.healthhubfordoctors.core.util.Resource
import dev.sobhy.healthhubfordoctors.profilefeature.domain.usecases.ChangePassUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePassViewModel
    @Inject
    constructor(
        private val changePassUseCase: ChangePassUseCase,
    ) : ViewModel() {
        private val validatePassword: ValidatePassword = ValidatePassword()
        private val _state = MutableStateFlow(ChangePassState())
        val state = _state.asStateFlow()

        fun onEvent(event: ChangePassEvent) {
            when (event) {
                is ChangePassEvent.CurrentPassChange -> {
                    _state.update {
                        it.copy(
                            currentPass = event.currentPass,
                        )
                    }
                }
                is ChangePassEvent.NewPassChange -> {
                    _state.update {
                        it.copy(
                            newPass = event.newPass,
                        )
                    }
                }
                is ChangePassEvent.ConfirmPassChange -> {
                    _state.update {
                        it.copy(
                            confirmPass = event.confirmPass,
                        )
                    }
                }

                ChangePassEvent.SavePass -> changePass()
            }
        }

        private fun changePass() {
            val currentPass = state.value.currentPass
            val newPass = state.value.newPass
            val confirmPass = state.value.confirmPass
            if (currentPass.isEmpty()) {
                return
            }
            if (newPass.isEmpty()) {
                return
            }
            if (confirmPass.isEmpty()) {
                return
            }
            val passwordResult = validatePassword.execute(state.value.newPass)
            if (!validatePassword.execute(newPass).successful) {
                _state.update {
                    it.copy(
                        error = passwordResult.errorMessage,
                    )
                }
                return
            }
            if (newPass != confirmPass) {
                _state.update {
                    it.copy(
                        error = "confirm password should be same as password",
                    )
                }
                return
            }
            viewModelScope.launch {
                changePassUseCase(currentPass, newPass).collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    error = result.message,
                                    isLoading = false,
                                    success = null,
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                    error = null,
                                    success = null,
                                )
                            }
                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    success = result.data,
                                    error = null,
                                    isLoading = false,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
