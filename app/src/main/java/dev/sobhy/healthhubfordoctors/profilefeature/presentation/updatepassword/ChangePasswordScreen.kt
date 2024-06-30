package dev.sobhy.healthhubfordoctors.profilefeature.presentation.updatepassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ChangePasswordScreen(viewModel: UpdatePassViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currPassChange =
        remember<(String) -> Unit> {
            {
                viewModel.onEvent(ChangePassEvent.CurrentPassChange(it))
            }
        }
    val newPassChange =
        remember<(String) -> Unit> {
            {
                viewModel.onEvent(ChangePassEvent.NewPassChange(it))
            }
        }
    val confPassChange =
        remember<(String) -> Unit> {
            {
                viewModel.onEvent(ChangePassEvent.ConfirmPassChange(it))
            }
        }
    val buttonClick =
        remember {
            {
                viewModel.onEvent(ChangePassEvent.SavePass)
            }
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(12.dp)
                .imePadding()
                .statusBarsPadding()
                .navigationBarsPadding(),
    ) {
        CurrentPassTextField(
            currentPass = state.currentPass,
            onCurrentPassChange = currPassChange,
        )
        NewPassTextField(
            newPass = state.newPass,
            onNewPassChange = newPassChange,
        )
        ConfirmPassTextField(
            confirmPass = state.confirmPass,
            onConfirmPassChange = confPassChange,
            buttonClick = buttonClick,
            modifier = Modifier.fillMaxWidth().padding(12.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        val buttonEnabled by remember {
            derivedStateOf {
                state.isButtonEnable
            }
        }
        Button(
            onClick = buttonClick,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            enabled = buttonEnabled,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "Save Password", style = MaterialTheme.typography.titleMedium)
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        state.error?.let {
            Text(
                text = it,
                style =
                    MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.error,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            )
        }
        state.success?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleSmall,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            )
        }
    }
}

@Composable
fun CurrentPassTextField(
    currentPass: String,
    onCurrentPassChange: (String) -> Unit,
) {
    var showCurrentPass by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = currentPass,
        onValueChange = onCurrentPassChange,
        label = {
            Text(text = "Current Password")
        },
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
        singleLine = true,
        visualTransformation =
            if (showCurrentPass) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
        trailingIcon = {
            if (isFocused) {
                IconButton(onClick = { showCurrentPass = !showCurrentPass }) {
                    val visibilityIcon =
                        if (showCurrentPass) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description = if (showCurrentPass) "Hide password" else "Show password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            }
        },
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
    )
}

@Composable
fun NewPassTextField(
    newPass: String,
    onNewPassChange: (String) -> Unit,
) {
    var showNewPass by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = newPass,
        onValueChange = onNewPassChange,
        label = {
            Text(text = "New Password")
        },
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
        singleLine = true,
        visualTransformation =
            if (showNewPass) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
        trailingIcon = {
            if (isFocused) {
                IconButton(onClick = { showNewPass = !showNewPass }) {
                    val visibilityIcon =
                        if (showNewPass) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description = if (showNewPass) "Hide password" else "Show password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            }
        },
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
    )
}

@Composable
fun ConfirmPassTextField(
    confirmPass: String,
    onConfirmPassChange: (String) -> Unit,
    buttonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showConfirmPass by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = confirmPass,
        onValueChange = onConfirmPassChange,
        label = {
            Text(text = "Confirm Password")
        },
        modifier =
            modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
        singleLine = true,
        visualTransformation =
            if (showConfirmPass) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
        trailingIcon = {
            if (isFocused) {
                IconButton(onClick = { showConfirmPass = !showConfirmPass }) {
                    val visibilityIcon =
                        if (showConfirmPass) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description = if (showConfirmPass) "Hide password" else "Show password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            }
        },
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
        keyboardActions =
            KeyboardActions(
                onDone = { buttonClick() },
            ),
    )
}
