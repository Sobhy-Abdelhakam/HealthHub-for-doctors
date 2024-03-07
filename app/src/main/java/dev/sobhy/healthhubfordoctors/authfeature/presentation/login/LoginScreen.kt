package dev.sobhy.healthhubfordoctors.authfeature.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel<LoginViewModel>()
    val state by viewModel.loginState.collectAsState()

    // lambda for each event to reduce recomposition
    val loginButtonLambda =
        remember {
            {
                viewModel.onEvent(LoginUiEvent.Login)
            }
        }
    val emailChanged =
        remember<(String) -> Unit> {
            {
                viewModel.onEvent(LoginUiEvent.EmailChanged(it))
            }
        }
    val passwordChanged =
        remember<(String) -> Unit> {
            {
                viewModel.onEvent(LoginUiEvent.PasswordChanged(it))
            }
        }

    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(18.dp),
    ) {
        item {
            Text(text = "Welcome Back", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = "Login to your account", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        item {
            EmailTextField(
                email = state.email,
                onEmailChange = emailChanged,
                emailError = state.emailError,
            )
        }
        item {
            PasswordTextField(
                password = state.password,
                onPasswordChange = passwordChanged,
                passwordError = state.passwordError,
                onDoneClick = loginButtonLambda,
            )
        }
        item {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Forgot password?", textDecoration = TextDecoration.Underline)
                }
            }
        }
        item {
            Button(
                onClick = loginButtonLambda,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                enabled = state.email.isNotBlank() && state.password.isNotBlank() && !state.isLoading,
            ) {
                Text(
                    text = "Login",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 4.dp),
                )
            }
        }
        if (state.isLoading) {
            item {
                CircularProgressIndicator()
            }
        }
        state.error?.let {
            item {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Don't have an account?")
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Sign up", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    onDoneClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier =
            Modifier
                .fillMaxWidth(),
        label = { Text(text = "Enter password") },
        singleLine = true,
        visualTransformation =
            if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                val visibilityIcon =
                    if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description = if (showPassword) "Hide password" else "Show password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        isError = passwordError != null,
        supportingText = {
            if (passwordError != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = passwordError,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                )
            }
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
        keyboardActions =
            KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onDoneClick()
                },
            ),
    )
}

@Composable
fun EmailTextField(
    email: String = "",
    onEmailChange: (String) -> Unit = {},
    emailError: String?,
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier =
            Modifier
                .fillMaxWidth(),
        label = { Text(text = "email") },
        placeholder = { Text(text = "example@gmail.com") },
        singleLine = true,
        isError = emailError != null,
        supportingText = {
            if (emailError != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = emailError,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                )
            }
        },
        trailingIcon = {
            if (emailError != null) {
                Icon(
                    Icons.Filled.Error,
                    "error",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
