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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.AddClinicScreenDestination
import com.ramcosta.composedestinations.generated.destinations.AvailabilityScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.sobhy.healthhubfordoctors.R
import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.GetClinicResponse

@Destination<RootGraph>
@Composable
fun ClinicListScreen(
    navigator: DestinationsNavigator,
    viewModel: ClinicsViewModel = hiltViewModel(),
) {
    val state by viewModel.clinicsState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.clinics.isEmpty()) {
            Text(
                text = stringResource(R.string.no_clinics),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center),
            )
            Button(
                onClick = {
                    navigator.navigate(AddClinicScreenDestination)
                },
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(32.dp),
            ) {
                Text(text = "Add Clinic")
            }
        } else {
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
            LazyColumn {
                items(state.clinics) {
                    ClinicDetailsItem(
                        clinic = it,
                        setAvailabilityClick = {
                            navigator.navigate(AvailabilityScreenDestination)
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun ClinicDetailsItem(
    modifier: Modifier = Modifier,
    clinic: GetClinicResponse,
    setAvailabilityClick: () -> Unit,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = clinic.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Clinic Number")
            Text(text = clinic.address)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row {
                    Text(text = stringResource(R.string.examination))
                    Text(text = "${clinic.examination} EGP", style = MaterialTheme.typography.titleMedium)
                }
                Row {
                    Text(text = stringResource(R.string.follow_up))
                    Text(text = "${clinic.followUp} EGP", style = MaterialTheme.typography.titleMedium)
                }
            }
            if (clinic.doctorAvailabilities == null) {
                Button(
                    onClick = setAvailabilityClick,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                ) {
                    Text(text = "setAvailability")
                }
            } else {
                clinic.doctorAvailabilities.forEach {
                    Row(modifier = modifier.fillMaxWidth()) {
                        Text(text = "${it.day}: ")
                        Text(text = "${it.startTime} - ${it.endTime}")
                    }
                }
            }
        }
    }
}
