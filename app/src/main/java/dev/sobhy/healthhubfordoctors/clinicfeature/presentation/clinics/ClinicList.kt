package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.clinics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.sobhy.healthhubfordoctors.R
import dev.sobhy.healthhubfordoctors.destinations.AddClinicScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ClinicListScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    val viewModel: ClinicsViewModel = viewModel()
    Box(modifier = modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = {
                navigator.navigate(AddClinicScreenDestination)
            },
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp),
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }

        if (viewModel.clinics.isEmpty()) {
            Text(
                text = stringResource(R.string.no_clinics),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.align(Alignment.Center),
            )
        } else {
            LazyColumn {
                items(viewModel.clinics.size) {
                    ClinicDetailsItem()
                }
            }
        }
    }
}

@Composable
fun ClinicDetailsItem(modifier: Modifier = Modifier) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Clinic Name", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Clinic Number")
            Text(text = "Clinic Address")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row {
                    Text(text = stringResource(R.string.examination))
                    Text(text = "150 EGP", style = MaterialTheme.typography.titleMedium)
                }
                Row {
                    Text(text = stringResource(R.string.follow_up))
                    Text(text = "90 EGP", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
