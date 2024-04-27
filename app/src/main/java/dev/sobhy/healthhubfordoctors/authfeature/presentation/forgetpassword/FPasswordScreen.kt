package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import dev.sobhy.healthhubfordoctors.R

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ForgetPasswordScreen(viewModel: FPasswordViewModel = hiltViewModel()) {
    val state by viewModel.forgetPasswordState.collectAsState()

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
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.forget_password_text),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(18.dp),
            )
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = viewModel::onEmailChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                singleLine = true,
                isError = state.emailError != null,
                supportingText = {
                    state.emailError?.let { emailError ->
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = emailError,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                        )
                    }
                },
            )
            Button(
                enabled = !state.isLoading,
                onClick = viewModel::sendEmail,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = stringResource(R.string.send),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            state.success?.let { success ->
                Text(
                    text = success,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ForgetPasswrodPrev() {
    ForgetPasswordScreen()
}
