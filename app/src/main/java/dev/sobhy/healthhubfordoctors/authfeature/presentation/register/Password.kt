package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sobhy.healthhubfordoctors.R

@Composable
fun SecurityInformation(
    modifier: Modifier = Modifier,
    password: () -> String,
    onPasswordChange: (String) -> Unit,
    isLoading: () -> Boolean,
    onRegisterClick: () -> Unit,
    errorText: @Composable () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Text(text = stringResource(R.string.security_information), style = MaterialTheme.typography.headlineSmall)
        }
        item {
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
//                Text(text = stringResource(R.string.register))
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
