package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

import android.content.Context
import android.location.Geocoder
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.sobhy.healthhubfordoctors.R
import dev.sobhy.healthhubfordoctors.navigation.ScreenRoutes
import dev.sobhy.healthhubfordoctors.ui.composables.Loader
import org.osmdroid.util.GeoPoint
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClinicScreen(
    navController: NavController,
    viewModel: AddClinicViewModel = hiltViewModel(),
) {
    val state by viewModel.addClinicState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val geoPointStateFlow =
        remember {
            navController.currentBackStackEntry?.savedStateHandle?.getStateFlow<GeoPoint>(
                "clinic_address",
                GeoPoint(0.0, 0.0),
            )
        }

    val geoPointState = geoPointStateFlow?.collectAsStateWithLifecycle()

    LaunchedEffect(geoPointState) {
        geoPointState?.value?.let {
            try {
                if (it != GeoPoint(0.0, 0.0)) {
                    viewModel.onEvent(AddClinicUiEvent.UpdateLocation(it.latitude, it.longitude))
                    val addressText = getAddressText(context, it)
                    viewModel.onEvent(AddClinicUiEvent.ClinicAddressChange(addressText))
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
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
                            navController.popBackStack()
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
                    onValueChange = { },
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
                            navController.navigate(ScreenRoutes.MapScreen.route)
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
    state.errorMessages?.let {
        AlertDialog(
            onDismissRequest = {
                viewModel.clearErrorMessage()
            },
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
