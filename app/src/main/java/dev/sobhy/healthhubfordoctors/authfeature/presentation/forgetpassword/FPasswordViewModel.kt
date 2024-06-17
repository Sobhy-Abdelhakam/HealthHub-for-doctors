package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
            private set
        var emailError by mutableStateOf<String?>(null)
            private set

        fun sendEmail() {
            val emailResult = validateEmail.execute(email)
            if (!emailResult.successful) {
                emailError = emailResult.errorMessage
                return
            }
            viewModelScope.launch(Dispatchers.IO) {
                forgetPasswordUseCase(email).collectLatest { result ->
                    _forgetPasswordState.value =
                        when (result) {
                            is Resource.Error -> ForgetPasswordState(error = result.message)

                            is Resource.Loading -> ForgetPasswordState(isLoading = true)

                            is Resource.Success -> ForgetPasswordState(success = result.data)
                        }
                }
            }
        }

        fun onEmailChange(input: String) {
            email = input
            emailError = null
        }
    }
