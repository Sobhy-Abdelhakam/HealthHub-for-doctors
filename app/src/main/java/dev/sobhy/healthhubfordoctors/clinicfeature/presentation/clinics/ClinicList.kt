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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.sobhy.healthhubfordoctors.R
import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.ClinicResponse
import dev.sobhy.healthhubfordoctors.navigation.ScreenRoutes
import dev.sobhy.healthhubfordoctors.ui.composables.Loader
import dev.sobhy.healthhubfordoctors.ui.theme.sdp

@Composable
fun ClinicListScreen(
    navController: NavController,
    viewModel: ClinicsViewModel = hiltViewModel(),
) {
    val state by viewModel.clinicsState.collectAsState()
    if (state.isLoading) {
        Loader()
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.clinics.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_clinics),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.Center),
                )
                Button(
                    onClick = {
                        navController.navigate(ScreenRoutes.AddClinic.route)
                    },
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(32.sdp),
                ) {
                    Text(text = "Add Clinic")
                }
            } else {
                LazyColumn {
                    items(state.clinics) { clinic ->
                        ClinicDetailsItem(
                            clinic = clinic,
                            setAvailabilityClick = {
                                navController.navigate("${ScreenRoutes.AvailabilityOfClinic.route}/$it")
                            },
                        )
                    }
                }
                FloatingActionButton(
                    onClick = {
                        navController.navigate(ScreenRoutes.AddClinic.route)
                    },
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(32.sdp),
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    }
}

@Composable
fun ClinicDetailsItem(
    modifier: Modifier = Modifier,
    clinic: ClinicResponse,
    setAvailabilityClick: (Int) -> Unit,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.sdp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.sdp),
    ) {
        Column(modifier = Modifier.padding(8.sdp)) {
            Text(
                text = clinic.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            if (clinic.phone.isNotEmpty()) {
                Text(text = clinic.phone)
            }
            Text(text = clinic.address)
            Row {
                Text(
                    text = stringResource(R.string.examination),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "${clinic.examination} EGP",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.follow_up),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "${clinic.followUp} EGP",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            if (clinic.doctorAvailabilities!!.isEmpty()) {
                Button(
                    onClick = { setAvailabilityClick(clinic.id) },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.sdp),
                ) {
                    Text(text = "setAvailability")
                }
            } else {
                OutlinedCard(
                    onClick = {
                        setAvailabilityClick(clinic.id)
                    },
                    modifier =
                        Modifier
                            .padding(12.sdp),
                ) {
                    clinic.doctorAvailabilities.filter {
                        it.available
                    }.forEach {
                        Row(
                            modifier = modifier.fillMaxWidth().padding(8.sdp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                text = "${it.day}: ",
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(
                                text = "${it.startTime} - ${it.endTime}",
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
