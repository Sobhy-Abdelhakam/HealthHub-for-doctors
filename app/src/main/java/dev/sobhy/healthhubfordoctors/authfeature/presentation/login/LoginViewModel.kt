package dev.sobhy.healthhubfordoctors.authfeature.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sobhy.healthhubfordoctors.authfeature.data.repository.AuthRepositoryImpl
import dev.sobhy.healthhubfordoctors.authfeature.domain.use_case.LoginUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.use_case.ValidateEmail
import dev.sobhy.healthhubfordoctors.authfeature.domain.use_case.ValidatePassword
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val loginUseCase: LoginUseCase = LoginUseCase(AuthRepositoryImpl()),
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged ->
                _loginState.update {
                    it.copy(
                        email = event.email,
                        emailError = null,
                    )
                }

            is LoginUiEvent.PasswordChanged ->
                _loginState.update {
                    it.copy(
                        password = event.password,
                        passwordError = null,
                    )
                }

            LoginUiEvent.Login -> login()
        }
    }

    private fun login() {
        val emailResult = validateEmail.execute(loginState.value.email)
        val passwordResult = validatePassword.execute(loginState.value.password)
        val hasError = listOf(emailResult, passwordResult).any { !it.successful }

        if (hasError) {
            _loginState.update {
                it.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                )
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(email = loginState.value.email, password = loginState.value.password)
                .collectLatest { result ->
                    _loginState.value =
                        when (result) {
                            is Resource.Loading ->
                                loginState.value.copy(isLoading = true, error = null)

                            is Resource.Error ->
                                loginState.value.copy(isLoading = false, error = result.message)

                            is Resource.Success -> {
                                Log.d("login", "login: success")
                                loginState.value.copy(
                                    isLoading = false,
                                    isSuccess = true,
                                    error = null,
                                )
                            }
                        }
                }
        }
    }
}
