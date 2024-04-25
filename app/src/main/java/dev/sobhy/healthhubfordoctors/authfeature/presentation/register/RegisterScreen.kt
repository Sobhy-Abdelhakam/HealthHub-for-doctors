package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.sobhy.healthhubfordoctors.R

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun RegisterScreen(destinationsNavigator: DestinationsNavigator) {
    val viewModel = viewModel<RegisterViewModel>()
    val state by viewModel.registerState.collectAsState()

    // lambda remembers for each event
    val nameChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(RegisterUiEvent.NameChange(it)) }
        }
    val emailChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(RegisterUiEvent.EmailChange(it)) }
        }
    val phoneNumberChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(RegisterUiEvent.PhoneNumberChange(it)) }
        }
    val genderChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(RegisterUiEvent.GenderChange(it)) }
        }
    val dateChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(RegisterUiEvent.DOBChange(it)) }
        }
    val specializationChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(RegisterUiEvent.SpecializationChange(it)) }
        }
    val professionalTitleChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(RegisterUiEvent.ProfessionalTitleChange(it)) }
        }
    val passwordChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(RegisterUiEvent.PasswordChange(it)) }
        }

//    if (state.success) {
//        onNavigateToHome()
//        return
//    }

    val fillAnyField by remember {
        derivedStateOf {
            state.name.isNotBlank() ||
                state.email.isNotBlank() ||
                state.phone.isNotBlank() ||
                state.gender.isNotBlank() ||
                state.dateOfBirth.isNotBlank()
        }
    }

    var showConfirmationDialog by remember { mutableStateOf(false) }
    BackHandler {
        if (fillAnyField) {
            showConfirmationDialog = true
        } else {
            destinationsNavigator.navigateUp()
        }
    }
    if (showConfirmationDialog) {
        ConfirmationDialog(
            onNavigateUp = destinationsNavigator::navigateUp,
            dismissDialog = { showConfirmationDialog = false },
        )
    }

    var currentStep by remember { mutableIntStateOf(1) }

    Scaffold(
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
                    name = { state.name },
                    onNameChange = nameChange,
                    email = { state.email },
                    onEmailChange = emailChange,
                    phoneNumber = { state.phone },
                    onPhoneNumberChange = phoneNumberChange,
                    gender = { state.gender },
                    onGenderChange = genderChange,
                    date = { state.dateOfBirth },
                    onDateChange = dateChange,
                    modifier =
                        Modifier
                            .padding(paddingValue)
                            .fillMaxSize(),
                    onNextClick = { currentStep++ },
                )
            }

            2 -> {
                ProfessionalInformation(
                    modifier =
                        Modifier
                            .padding(paddingValue)
                            .fillMaxSize(),
                    specialization = { state.specialization },
                    onSpecializationChange = specializationChange,
                    professionalTitle = { state.professionalTitle },
                    onProfessionalTitleChange = professionalTitleChange,
                    onNextClick = { currentStep++ },
                )
            }

            3 -> {
                SecurityInformation(
                    modifier =
                        Modifier
                            .padding(paddingValue)
                            .fillMaxSize(),
                    password = { state.password },
                    onPasswordChange = passwordChange,
                    isLoading = state.isLoading,
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

@Composable
fun ConfirmationDialog(
    onNavigateUp: () -> Unit,
    dismissDialog: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(
                text = stringResource(R.string.are_you_sure),
            )
        },
        text = {
            Text(
                text = stringResource(R.string.alert_dialog_text),
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    dismissDialog()
                    onNavigateUp()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.yes_go_back))
            }
        },
        dismissButton = {
            TextButton(
                onClick = dismissDialog,
            ) {
                Text(text = stringResource(R.string.no_stay_here))
            }
        },
        onDismissRequest = {},
    )
}
