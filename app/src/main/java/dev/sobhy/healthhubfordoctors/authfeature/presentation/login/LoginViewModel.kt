package dev.sobhy.healthhubfordoctors.authfeature.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var state by mutableStateOf(LoginState())

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> state = state.copy(email = event.email)
            is LoginUiEvent.PasswordChanged -> state = state.copy(password = event.password)
            LoginUiEvent.Login -> login()
        }
    }

    private fun login() {
        // TODO: Implement login logic
    }
}