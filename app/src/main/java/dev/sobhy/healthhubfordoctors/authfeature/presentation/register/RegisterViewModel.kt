package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.authfeature.domain.use_case.RegisterUseCase
import dev.sobhy.healthhubfordoctors.core.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
    @Inject
    constructor(
        private val registerUseCase: RegisterUseCase,
    ) : ViewModel() {
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
            viewModelScope.launch {
                registerUseCase(
                    name = registerState.value.name,
                    email = registerState.value.email,
                    password = registerState.value.password,
                    phone = registerState.value.phone,
                    dateOfBirth = registerState.value.dateOfBirth,
                    gender = registerState.value.gender,
                    professionalTitle = registerState.value.professionalTitle,
                    specialization = registerState.value.specialization,
                ).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _registerState.value =
                                registerState.value.copy(isLoading = true)
                            Log.d("loading", "loading..")
                        }

                        is Resource.Error -> {
                            _registerState.value =
                                registerState.value.copy(isLoading = false, error = result.message)
                            Log.e("error", result.message.toString())
                        }

                        is Resource.Success -> {
                            _registerState.value =
                                registerState.value.copy(
                                    isLoading = false,
                                    success = true,
                                    error = null,
                                )
                            Log.d("user", result.data.toString())
                        }
                    }
                }
            }
        }
    }
