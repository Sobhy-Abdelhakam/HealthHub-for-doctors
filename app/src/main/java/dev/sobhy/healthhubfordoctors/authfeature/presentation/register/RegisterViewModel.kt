package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.RegisterUseCase
import dev.sobhy.healthhubfordoctors.authfeature.domain.usecase.ValidatePassword
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
    @Inject
    constructor(
        private val registerUseCase: RegisterUseCase,
    ) : ViewModel() {
        private val validatePassword: ValidatePassword = ValidatePassword()
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
                is RegisterUiEvent.ProfessionalTitleChange ->
                    _registerState.update {
                        it.copy(
                            professionalTitle = event.professionalTitle,
                        )
                    }

                is RegisterUiEvent.SpecializationChange ->
                    _registerState.update {
                        it.copy(
                            specialization = event.specialization,
                        )
                    }

                RegisterUiEvent.Register -> register()
            }
        }

        fun register() {
            val passwordResult = validatePassword.execute(registerState.value.password)
            if (!passwordResult.successful) {
                _registerState.update {
                    it.copy(
                        passwordError = passwordResult.errorMessage,
                    )
                }
                return
            }
            viewModelScope.launch(Dispatchers.IO) {
                registerUseCase(
                    name = registerState.value.name,
                    email = registerState.value.email,
                    password = registerState.value.password,
                    phone = "+20" + registerState.value.phone,
                    dateOfBirth = registerState.value.dateOfBirth,
                    gender = registerState.value.gender,
                    professionalTitle = registerState.value.professionalTitle,
                    specialization = registerState.value.specialization,
                ).collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _registerState.value =
                                registerState.value.copy(isLoading = true)
                        }

                        is Resource.Error -> {
                            _registerState.value =
                                registerState.value.copy(isLoading = false, error = result.message)
                        }

                        is Resource.Success -> {
                            _registerState.value =
                                registerState.value.copy(
                                    isLoading = false,
                                    success = true,
                                    error = null,
                                )
                        }
                    }
                }
            }
        }
    }
