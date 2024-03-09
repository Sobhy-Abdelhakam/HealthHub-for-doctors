package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.sobhy.healthhubfordoctors.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel<RegisterViewModel>()
    val state by viewModel.registerState.collectAsState()

    var currentStep by remember { mutableIntStateOf(1) }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.create_account),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
            )
        },
        bottomBar = {
            BottomAppBar {
                StepIndicator(
                    modifier =
                        Modifier
                            .weight(1f)
                            .align(Alignment.Bottom),
                    currentStep = currentStep,
                    totalSteps = 3,
                    onStepClick = { currentStep = it },
                )
            }
        },
    ) { paddingValue ->
        when (currentStep) {
            1 -> {
                PersonalInformation(
                    name = state.name,
                    onNameChange = { viewModel.onEvent(RegisterUiEvent.NameChange(it)) },
                    email = state.email,
                    onEmailChange = { viewModel.onEvent(RegisterUiEvent.EmailChange(it)) },
                    phoneNumber = state.phone,
                    onPhoneNumberChange = { viewModel.onEvent(RegisterUiEvent.PhoneNumberChange(it)) },
                    gender = state.gender,
                    onGenderChange = { viewModel.onEvent(RegisterUiEvent.GenderChange(it)) },
                    date = state.dateOfBirth,
                    onDateChange = { viewModel.onEvent(RegisterUiEvent.DOBChange(it)) },
                    modifier =
                        Modifier
                            .padding(paddingValue)
                            .fillMaxSize(),
                ) {
                    currentStep++
                }
            }

            2 -> {
                ProfessionalInformation(
                    modifier =
                        Modifier
                            .padding(paddingValue)
                            .fillMaxSize(),
                    specialization = state.specialization,
                    onSpecializationChange = {
                        viewModel.onEvent(
                            RegisterUiEvent.SpecializationChange(
                                it,
                            ),
                        )
                    },
                    professionalTitle = state.professionalTitle,
                    onProfessionalTitleChange = {
                        viewModel.onEvent(
                            RegisterUiEvent.ProfessionalTitleChange(
                                it,
                            ),
                        )
                    },
                ) {
                    currentStep++
                }
            }

            3 -> {
                SecurityInformation(
                    modifier =
                        Modifier
                            .padding(paddingValue)
                            .fillMaxSize(),
                    password = state.password,
                    onPasswordChange = { viewModel.onEvent(RegisterUiEvent.PasswordChange(it)) },
                    onRegisterClick = { viewModel.onEvent(RegisterUiEvent.Register) },
                )
            }
        }
    }
}

@Composable
fun StepIndicator(
    modifier: Modifier = Modifier,
    currentStep: Int,
    totalSteps: Int,
    onStepClick: (Int) -> Unit,
) {
    for (step in 1..totalSteps) {
        Box(
            modifier =
                modifier
                    .size(8.dp)
                    .padding(horizontal = 8.dp)
                    .background(
                        color =
                            if (step <= currentStep) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                            },
                        shape = CircleShape,
                    )
                    .clickable {
                        if (step <= currentStep) {
                            onStepClick(step)
                        }
                    },
        )
    }
}
