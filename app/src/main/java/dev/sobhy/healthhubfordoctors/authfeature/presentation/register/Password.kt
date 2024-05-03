package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.sobhy.healthhubfordoctors.R

@Composable
fun SecurityInformation(
    modifier: Modifier = Modifier,
    password: () -> String,
    onPasswordChange: (String) -> Unit,
    passwordError: () -> String?,
    isLoading: () -> Boolean,
    onRegisterClick: () -> Unit,
    errorText: @Composable () -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Text(text = stringResource(R.string.security_information), style = MaterialTheme.typography.headlineSmall)
        }
        item {
            var showPassword by remember { mutableStateOf(false) }
            TextField(
                value = password(),
                onValueChange = onPasswordChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                label = {
                    Text(text = stringResource(id = R.string.enter_password))
                },
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
                isError = passwordError() != null,
                supportingText = {
                    if (passwordError() != null) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = passwordError()!!,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                        )
                    }
                },
            )
        }
        item {
            val buttonEnabled by remember {
                derivedStateOf { password().isNotBlank() && !isLoading() }
            }
            Button(
                enabled = buttonEnabled,
                onClick = onRegisterClick,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                if (isLoading()) {
                    CircularProgressIndicator()
                } else {
                    Text(text = stringResource(R.string.register))
                }
            }
            errorText()
        }
    }
}
