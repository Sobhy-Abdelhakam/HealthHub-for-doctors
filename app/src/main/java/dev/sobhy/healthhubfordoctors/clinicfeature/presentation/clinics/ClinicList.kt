package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.clinics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.sobhy.healthhubfordoctors.destinations.AddClinicScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ClinicListScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Clinics", style = MaterialTheme.typography.displayMedium)
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navigator.navigate(AddClinicScreenDestination)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
    ) {
        ClinicDetailsItem(modifier = Modifier.padding(it))
    }
}

@Composable
fun ClinicDetailsItem(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp),
    ) {
        Icon(
            imageVector = Icons.Default.MoreHoriz,
            contentDescription = "Manage",
            modifier = Modifier.align(Alignment.TopEnd),
        )
        Column {
            Text(text = "Clinic Name", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Clinic Number")
            Text(text = "Clinic Address")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row {
                    Text(text = "Examination: ")
                    Text(text = "150 EGP")
                }
                Row {
                    Text(text = "Follow_up: ")
                    Text(text = "90 EGP")
                }
            }
        }
    }
}
