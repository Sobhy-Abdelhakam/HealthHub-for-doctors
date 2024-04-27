package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.authfeature.domain.use_case.ForgetPasswordUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.use_case.ValidateEmail
import dev.sobhy.healthhubfordoctors.core.util.Resource
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

        var email by mutableStateOf("")

        fun onEmailChange(email: String) {
            this.email = email
            _forgetPasswordState.update {
                it.copy(
                    emailError = null,
                )
            }
        }

        fun sendEmail() {
            val emailResult = validateEmail.execute(email)
            if (!emailResult.successful) {
                _forgetPasswordState.update {
                    it.copy(
                        emailError = emailResult.errorMessage,
                    )
                }
                return
            }
            viewModelScope.launch {
                forgetPasswordUseCase(email).collectLatest { result ->
                    _forgetPasswordState.value =
                        when (result) {
                            is Resource.Error ->
                                forgetPasswordState.value.copy(
                                    isLoading = false,
                                    error = result.message,
                                )

                            is Resource.Loading ->
                                forgetPasswordState.value.copy(
                                    isLoading = true,
                                    error = null,
                                )

                            is Resource.Success ->
                                forgetPasswordState.value.copy(
                                    isLoading = false,
                                    success = result.data.toString(),
                                    error = null,
                                )
                        }
                }
            }
        }
    }
