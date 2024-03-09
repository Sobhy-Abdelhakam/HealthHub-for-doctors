package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SecurityInformation(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Text(text = "Security Information", style = MaterialTheme.typography.headlineSmall)
        }
        item {
            TextField(
                value = password,
                onValueChange = onPasswordChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                label = {
                    Text(text = "password")
                },
            )
        }
    }
}
