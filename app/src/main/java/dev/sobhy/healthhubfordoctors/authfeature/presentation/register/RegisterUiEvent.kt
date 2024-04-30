package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import java.time.LocalDate

sealed class RegisterUiEvent {
    data class NameChange(val name: String) : RegisterUiEvent()

    data class EmailChange(val email: String) : RegisterUiEvent()

    data class PhoneNumberChange(val phoneNumber: String) : RegisterUiEvent()

    data class GenderChange(val gender: String) : RegisterUiEvent()

    data class DOBChange(val dateOfBirth: LocalDate) : RegisterUiEvent()

    data class SpecializationChange(val specialization: String) : RegisterUiEvent()

    data class ProfessionalTitleChange(val professionalTitle: String) : RegisterUiEvent()

    data class PasswordChange(val password: String) : RegisterUiEvent()

    data object Register : RegisterUiEvent()
}
