package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ClinicAddressScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.onResult
import dev.sobhy.healthhubfordoctors.R
import dev.sobhy.healthhubfordoctors.ui.composables.Loader
import org.osmdroid.util.GeoPoint
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun AddClinicScreen(
    destinationsNavigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<ClinicAddressScreenDestination, GeoPoint>,
    viewModel: AddClinicViewModel = hiltViewModel(),
) {
    val state by viewModel.addClinicState.collectAsState()
    val context = LocalContext.current
    resultRecipient.onResult { resultValue ->
        val latitude = resultValue.latitude
        val longitude = resultValue.longitude
        viewModel.onEvent(AddClinicUiEvent.UpdateLocation(latitude, longitude))
        val addressText = getAddressText(context, resultValue)
        viewModel.onEvent(AddClinicUiEvent.ClinicAddressChange(addressText))
    }
    val nameChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(AddClinicUiEvent.ClinicNameChange(it)) }
        }
    val phoneChange =
        remember<(String) -> Unit> {
            { viewModel.onEvent(AddClinicUiEvent.ClinicPhoneChange(it)) }
        }
    val examinationChange =
        remember<(String) -> Unit> {
            {
                viewModel.onEvent(
                    (
                        AddClinicUiEvent.ExaminationChange(
                            it.filter { symbol ->
                                symbol.isDigit()
                            },
                        )
                    ),
                )
            }
        }
    val followUpChange =
        remember<(String) -> Unit> {
            {
                viewModel.onEvent(
                    AddClinicUiEvent.FollowUpChange(
                        it.filter { symbol ->
                            symbol.isDigit()
                        },
                    ),
                )
            }
        }

    val saveBtnEnable by remember {
        derivedStateOf {
            state.clinicName.isNotBlank() &&
                state.clinicAddress.isNotBlank() &&
                state.examination.isNotBlank() &&
                state.followUp.isNotBlank()
        }
    }
    if (state.loading) {
        Loader()
    }
    state.errorMessages?.let {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = { /*TODO*/ },
            title = { Text(text = "Error") },
            text = { Text(text = it) },
            dismissButton = { /*TODO*/ },
            modifier = Modifier.padding(16.dp),
            containerColor = MaterialTheme.colorScheme.errorContainer,
            textContentColor = MaterialTheme.colorScheme.error,
            icon = {
                Icon(imageVector = Icons.Default.Error, contentDescription = "")
            },
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_clinic),
                        style = MaterialTheme.typography.displayMedium,
                    )
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
                    onValueChange = nameChange,
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
                    onValueChange = phoneChange,
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
                    onValueChange = examinationChange,
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
                    onValueChange = followUpChange,
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
        }
    }
}

fun getAddressText(
    context: Context,
    geoPoint: GeoPoint,
): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses =
        geocoder
            .getFromLocation(geoPoint.latitude, geoPoint.longitude, 1)
    val address = addresses?.firstOrNull()
    val locality = address?.locality ?: ""
    val adminArea = address?.adminArea ?: ""
    val countryName = address?.countryName ?: ""
    val subAdminArea = address?.subAdminArea ?: ""
    return "$locality, $subAdminArea, $adminArea, $countryName"
}
