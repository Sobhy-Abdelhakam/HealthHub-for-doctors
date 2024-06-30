package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.ForgetPasswordUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.ValidateEmail
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FPasswordViewModel
    @Inject
    constructor(
        private val forgetPasswordUseCase: ForgetPasswordUseCase,
    ) : ViewModel() {
        private val validateEmail: ValidateEmail = ValidateEmail()

        private val _forgetPasswordState = MutableStateFlow(ForgetPasswordState())
        val forgetPasswordState = _forgetPasswordState.asStateFlow()

        fun onEvent(event: ForgotPasswordEvent) {
            when (event) {
                is ForgotPasswordEvent.EnterEmail -> {
                    _forgetPasswordState.value = _forgetPasswordState.value.copy(email = event.email)
                }

                is ForgotPasswordEvent.EnterOtp -> {
                    _forgetPasswordState.value = _forgetPasswordState.value.copy(otp = event.otp)
                }

                is ForgotPasswordEvent.EnterNewPassword -> {
                    _forgetPasswordState.value =
                        _forgetPasswordState.value.copy(newPassword = event.newPassword)
                }

                ForgotPasswordEvent.SendOtp -> {
                    sendOtp()
                }

                ForgotPasswordEvent.SubmitNewPassword -> {
                    submitNewPassword()
                }
            }
        }

        fun sendOtp() {
            val emailResult = validateEmail.execute(forgetPasswordState.value.email)
            if (!emailResult.successful) {
                _forgetPasswordState.update {
                    it.copy(error = emailResult.errorMessage)
                }
                return
            }
            viewModelScope.launch(Dispatchers.IO) {
                _forgetPasswordState.value = _forgetPasswordState.value.copy(isLoading = true)
                forgetPasswordUseCase(forgetPasswordState.value.email).collectLatest { result ->
                    _forgetPasswordState.value =
                        when (result) {
                            is Resource.Error -> {
                                _forgetPasswordState.value.copy(
                                    isLoading = false,
                                    error = result.message,
                                )
                            }

                            is Resource.Loading -> {
                                _forgetPasswordState.value.copy(isLoading = true)
                            }

                            is Resource.Success -> {
                                _forgetPasswordState.value.copy(isLoading = false, isOtpSent = true)
                            }
                        }
                }
            }
        }

        private fun submitNewPassword() {
            viewModelScope.launch {
                _forgetPasswordState.value = _forgetPasswordState.value.copy(isLoading = true)
                try {
                    forgetPasswordUseCase.submitNewPassword(
                        _forgetPasswordState.value.email,
                        _forgetPasswordState.value.otp,
                        _forgetPasswordState.value.newPassword,
                    ).collectLatest { result ->
                        when (result) {
                            is Resource.Error -> {
                                _forgetPasswordState.update {
                                    it.copy(isLoading = false, error = result.message)
                                }
                            }
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                _forgetPasswordState.update {
                                    it.copy(isLoading = false, changeSuccess = true)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    _forgetPasswordState.value =
                        _forgetPasswordState.value.copy(isLoading = false, error = e.message)
                }
            }
        }
    }
