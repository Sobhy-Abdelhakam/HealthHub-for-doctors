package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.availability

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.sobhy.healthhubfordoctors.R
import java.time.DayOfWeek
import java.time.LocalTime

@Destination<RootGraph>
@Composable
fun AvailabilityScreen(
    destinationsNavigator: DestinationsNavigator,
    availabilityViewModel: AvailabilityViewModel = hiltViewModel(),
) {
    val state by availabilityViewModel.availabilityState.collectAsState()

    Scaffold { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(state.dayAvailable.entries.toList()) { (day, state) ->
                AvailabilityItem(
                    day = day,
                    isOn = state.isSwitchOn,
                    from = state.from,
                    to = state.to,
                    onSwitchChange = {
                        availabilityViewModel.onSwitchChanged(day, it)
                    },
                    onFromChange = {
                        availabilityViewModel.onFromChange(day, it)
                    },
                    onToChange = {
                        availabilityViewModel.onToChange(day, it)
                    },
                )
            }
            item {
                Button(onClick = {
                    destinationsNavigator.navigateUp()
                    availabilityViewModel.setAvailability()
                }) {
                    Text(text = "Save Availability")
                }
            }
        }
    }
}

@Composable
fun AvailabilityItem(
    day: DayOfWeek,
    isOn: Boolean,
    from: LocalTime,
    to: LocalTime,
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
        DaySwitch(day, isOn, onSwitchChange)
        AnimatedVisibility(visible = isOn) {
            TimePickers(from, to, onFromChange, onToChange)
        }
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
    from: LocalTime,
    to: LocalTime,
    onFromChange: (LocalTime) -> Unit,
    onToChange: (LocalTime) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimePicker(stringResource(R.string.from), onFromChange, from)
        TimePicker(stringResource(R.string.to), onToChange, to)
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
        Text(text = label, style = MaterialTheme.typography.titleMedium)
        TimeInput(
            state = timePickerState,
            modifier = Modifier.padding(4.dp),
        )
        onTimeChange(LocalTime.of(timePickerState.hour, timePickerState.minute))
    }
}
