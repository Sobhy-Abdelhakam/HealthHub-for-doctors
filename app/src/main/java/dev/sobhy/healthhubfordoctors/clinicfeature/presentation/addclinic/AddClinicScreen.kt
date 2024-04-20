package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddClinicScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Add Clinic", style = MaterialTheme.typography.displayMedium)
            })
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "Clinic Name")
                },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "Clinic Address")
                },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "phone number")
                },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
            )
        }
    }
}
