package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ClinicAddressScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.sobhy.healthhubfordoctors.R
import java.time.DayOfWeek
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun AddClinicScreen(destinationsNavigator: DestinationsNavigator) {
    val viewModel: AddClinicViewModel = viewModel()
    val state by viewModel.addClinicState.collectAsState()

    val saveBtnEnable by remember {
        derivedStateOf {
            state.clinicName.isNotBlank() &&
                state.clinicAddress.isNotBlank() &&
                state.examination.isNotBlank() &&
                state.followUp.isNotBlank()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.add_clinic), style = MaterialTheme.typography.displayMedium)
                },
                actions = {
                    Button(
                        onClick = {
                            viewModel.onEvent(AddClinicUiEvent.SaveClinic)
                        },
                        modifier = Modifier.padding(8.dp),
                        enabled = saveBtnEnable,
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                },
            )
        },
    ) { paddingValue ->
        LazyColumn(modifier = Modifier.padding(paddingValue)) {
            item {
                OutlinedTextField(
                    value = state.clinicName,
                    onValueChange = { viewModel.onEvent(AddClinicUiEvent.ClinicNameChange(it)) },
                    label = {
                        Text(text = stringResource(R.string.clinic_name))
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                )
            }
            item {
                OutlinedTextField(
                    value = state.clinicAddress,
                    onValueChange = { viewModel.onEvent(AddClinicUiEvent.ClinicAddressChange(it)) },
                    label = {
                        Text(text = stringResource(R.string.clinic_address))
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    singleLine = true,
                    readOnly = true,
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                    trailingIcon = {
                        IconButton(onClick = {
                            destinationsNavigator.navigate(ClinicAddressScreenDestination)
                        }) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                        }
                    },
                )
            }
            item {
                OutlinedTextField(
                    value = state.clinicNumber,
                    onValueChange = { viewModel.onEvent(AddClinicUiEvent.ClinicPhoneChange(it)) },
                    label = {
                        Text(text = stringResource(R.string.phone_number_optional))
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                )
            }
            item {
                OutlinedTextField(
                    value = state.examination,
                    onValueChange = {
                        viewModel.onEvent(
                            AddClinicUiEvent.ExaminationChange(
                                it.filter { symbol ->
                                    symbol.isDigit()
                                },
                            ),
                        )
                    },
                    label = {
                        Text(text = stringResource(R.string.examination_egp))
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                )
            }
            item {
                OutlinedTextField(
                    value = state.followUp,
                    onValueChange = {
                        viewModel.onEvent(
                            AddClinicUiEvent.FollowUpChange(
                                it.filter { symbol ->
                                    symbol.isDigit()
                                },
                            ),
                        )
                    },
                    label = {
                        Text(text = stringResource(R.string.follow_up_egp))
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                )
            }
            item {
                Text(
                    text = stringResource(R.string.availability),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp),
                )
            }
            item {
                DayOfWeek.entries.forEach { day ->
                    AvailabilityItem(
                        day = day,
                        dayState = state.availability.dayAvailable[day]!!,
                        onSwitchChange = { switchState ->
                            viewModel.onEvent(AddClinicUiEvent.UpdateSwitchState(day, switchState))
                        },
                        onFromChange = {
                            viewModel.onEvent(AddClinicUiEvent.UpdateFrom(day, it))
                        },
                        onToChange = {
                            viewModel.onEvent(AddClinicUiEvent.UpdateTo(day, it))
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun AvailabilityItem(
    day: DayOfWeek,
    dayState: DayState,
    onSwitchChange: (Boolean) -> Unit,
    onFromChange: (LocalTime) -> Unit,
    onToChange: (LocalTime) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
    ) {
        DaySwitch(day, dayState.isSwitchOn, onSwitchChange)
        TimePickers(dayState, onFromChange, onToChange)
    }
}

@Composable
fun DaySwitch(
    day: DayOfWeek,
    isSwitchOn: Boolean,
    onSwitchChange: (Boolean) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = day.toString(), fontSize = 18.sp)
        Switch(
            checked = isSwitchOn,
            onCheckedChange = onSwitchChange,
        )
    }
}

@Composable
fun TimePickers(
    dayState: DayState,
    onFromChange: (LocalTime) -> Unit,
    onToChange: (LocalTime) -> Unit,
) {
    AnimatedVisibility(visible = dayState.isSwitchOn) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TimePicker(stringResource(R.string.from), onFromChange, dayState.from)
            TimePicker(stringResource(R.string.to), onToChange, dayState.to)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    label: String,
    onTimeChange: (LocalTime) -> Unit,
    time: LocalTime,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val timePickerState =
            rememberTimePickerState(
                initialHour = time.hour,
                initialMinute = time.minute,
            )
        Text(text = label, style = MaterialTheme.typography.headlineMedium)
        TimeInput(
            state = timePickerState,
            modifier = Modifier.padding(4.dp),
        )
        onTimeChange(LocalTime.of(timePickerState.hour, timePickerState.minute))
    }
}
