package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.sobhy.healthhubfordoctors.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(
    navController: NavController,
    viewModel: FPasswordViewModel = hiltViewModel(),
) {
    val state by viewModel.forgetPasswordState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .fillMaxSize()
                    .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (!state.isOtpSent) {
                EmailInput(
                    email = state.email,
                    onEmailChange = { viewModel.onEvent(ForgotPasswordEvent.EnterEmail(it)) },
                    onSendOtpClick = { viewModel.onEvent(ForgotPasswordEvent.SendOtp) },
                )
            } else {
                OtpInput(
                    otp = state.otp,
                    onOtpChange = { viewModel.onEvent(ForgotPasswordEvent.EnterOtp(it)) },
                    newPassword = state.newPassword,
                    onNewPasswordChange = {
                        viewModel.onEvent(
                            ForgotPasswordEvent.EnterNewPassword(
                                it,
                            ),
                        )
                    },
                    onSubmitClick = { viewModel.onEvent(ForgotPasswordEvent.SubmitNewPassword) },
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            if (state.changeSuccess) {
                navController.navigateUp()
            }
        }
    }
}

@Composable
fun EmailInput(
    email: String,
    onEmailChange: (String) -> Unit,
    onSendOtpClick: () -> Unit,
) {
    Column {
        Text(
            text = stringResource(R.string.forget_password_text),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(18.dp),
        )
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSendOtpClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Send OTP")
        }
    }
}

@Composable
fun OtpInput(
    otp: String,
    onOtpChange: (String) -> Unit,
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
) {
    Column {
        OutlinedTextField(
            value = otp,
            onValueChange = onOtpChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
            label = {
                Text(text = "OTP")
            },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = newPassword,
            onValueChange = onNewPasswordChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
            label = {
                Text(text = "new Password")
            },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSubmitClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Submit New Password")
        }
    }
}
