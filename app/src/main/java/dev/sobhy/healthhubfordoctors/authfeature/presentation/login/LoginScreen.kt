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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.sobhy.healthhubfordoctors.R
import dev.sobhy.healthhubfordoctors.navigation.AuthenticationRoutes
import dev.sobhy.healthhubfordoctors.navigation.ScreenRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.loginState.collectAsStateWithLifecycle()
    if (state.isSuccess) {
        navController.popBackStack()
        navController.navigate(ScreenRoutes.BottomBar.route)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.welcome_back),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .padding(paddingValues)
                    .padding(18.dp),
        ) {
            item {
                Text(
                    text = stringResource(R.string.login_to_your_account),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                EmailTextField(
                    email = viewModel.email,
                    onEmailChange = { viewModel.onEvent(LoginUiEvent.UpdateEmail(it)) },
                    emailError = viewModel.emailError,
                )
            }
            item {
                PasswordTextField(
                    password = viewModel.password,
                    onPasswordChange = { viewModel.onEvent(LoginUiEvent.UpdatePassword(it)) },
                    passwordError = viewModel.passwordError,
                    onDoneClick = { viewModel.onEvent(LoginUiEvent.Login) },
                )
            }
            item {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = {
                        navController.navigate(AuthenticationRoutes.FORGOTPASSWORD.route)
                    }) {
                        Text(
                            text = stringResource(R.string.forgot_password),
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                }
            }
            item {
                val loginButtonEnabled by remember {
                    derivedStateOf {
                        viewModel.email.isNotBlank() && viewModel.password.isNotBlank() && !state.isLoading
                    }
                }
                Button(
                    onClick = { viewModel.onEvent(LoginUiEvent.Login) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    enabled = loginButtonEnabled,
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = MaterialTheme.typography.titleLarge,
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
                    Text(text = stringResource(R.string.don_t_have_an_account))
                    TextButton(onClick = {
                        navController.navigate(AuthenticationRoutes.REGISTER.route)
                    }) {
                        Text(text = stringResource(R.string.sign_up), fontWeight = FontWeight.Bold)
                    }
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
        label = { Text(text = stringResource(R.string.enter_password)) },
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
        label = { Text(text = stringResource(R.string.email)) },
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
