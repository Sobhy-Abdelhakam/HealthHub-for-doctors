package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.DOBChange -> _registerState.update { it.copy(dateOfBirth = event.dateOfBirth) }
            is RegisterUiEvent.EmailChange -> _registerState.update { it.copy(email = event.email) }
            is RegisterUiEvent.GenderChange -> _registerState.update { it.copy(gender = event.gender) }
            is RegisterUiEvent.NameChange -> _registerState.update { it.copy(name = event.name) }
            is RegisterUiEvent.PasswordChange -> _registerState.update { it.copy(password = event.password) }
            is RegisterUiEvent.PhoneNumberChange -> _registerState.update { it.copy(phone = event.phoneNumber) }
            is RegisterUiEvent.ProfessionalTitleChange -> _registerState.update { it.copy(professionalTitle = event.professionalTitle) }
            is RegisterUiEvent.SpecializationChange -> _registerState.update { it.copy(specialization = event.specialization) }
            RegisterUiEvent.Register -> TODO()
        }
    }
}
