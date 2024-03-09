package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.DOBChange -> TODO()
            is RegisterUiEvent.EmailChange -> TODO()
            is RegisterUiEvent.GenderChange -> TODO()
            is RegisterUiEvent.NameChange -> TODO()
            is RegisterUiEvent.PasswordChange -> TODO()
            is RegisterUiEvent.PhoneNumberChange -> TODO()
            is RegisterUiEvent.ProfessionalTitleChange -> TODO()
            RegisterUiEvent.Register -> TODO()
            is RegisterUiEvent.SpecializationChange -> TODO()
        }
    }
}
