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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.padding(18.dp)
    ) {
        item {
            Text(text = "Welcome Back", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = "Login to your account", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        item {
            EmailTextField(email = email, onEmailChange = {email = it}, emailError = null)
        }
        item {
            PasswordTextField(password = password, onPasswordChange = {password = it}, passwordError = null, onDoneClick = {})
        }
        item {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Forgot password?")
                }
            }
        }
        item {
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()){ Text(text = "Login") }
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
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
    onDoneClick: () -> Unit
) {
    var showPassword by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text(text = "password")
        },
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(onClick = {
                showPassword = !showPassword
            }) {
                Icon(
                    imageVector = if (showPassword) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    },
                    contentDescription = "password visibility",
                )
            }
        },
        isError = passwordError != null,
        supportingText = {
            if (passwordError != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = passwordError,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneClick()
            },
        ),
    )
}

@Composable
fun EmailTextField(email: String = "", onEmailChange: (String) -> Unit = {}, emailError: String?) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text(text = "email")
        },
        singleLine = true,
        isError = emailError != null,
        supportingText = {
            if (emailError != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = emailError,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        trailingIcon = {
            if (emailError != null) {
                Icon(Icons.Filled.Error,
                    "error",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(
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
