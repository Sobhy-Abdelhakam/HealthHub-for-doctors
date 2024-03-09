package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfessionalInformation(
    modifier: Modifier = Modifier,
    specialization: String,
    onSpecializationChange: (String) -> Unit,
    professionalTitle: String,
    onProfessionalTitleChange: (String) -> Unit,
    onNextClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Text(
                text = "Professional Information",
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        item {
            TextField(
                value = specialization,
                onValueChange = onSpecializationChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                label = {
                    Text(text = "specialization")
                },
            )
        }
        item {
            TextField(
                value = professionalTitle,
                onValueChange = onProfessionalTitleChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                label = {
                    Text(text = "professional title")
                },
            )
        }
        item {
            Row(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    enabled = specialization.isNotEmpty() && professionalTitle.isNotEmpty(),
                    onClick = onNextClick,
                ) {
                    Text(text = "Next")
                    Icon(imageVector = Icons.Default.KeyboardDoubleArrowRight, contentDescription = null)
                }
            }
        }
    }
}
