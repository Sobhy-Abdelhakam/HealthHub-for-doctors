package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.map

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Destination<RootGraph>
@Composable
fun ClinicAddressScreen() {
    val context = LocalContext.current
    var location by remember { mutableStateOf("") }
    val locationPermissionState =
        rememberMultiplePermissionsState(
            permissions =
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
        )
    val gpsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(context, "GPS Enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "GPS Disabled", Toast.LENGTH_SHORT).show()
            }
        }

    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Clinic Address",
                        modifier = Modifier.padding(16.dp),
                    )
                },
                actions = {
                    Button(onClick = {
                        Toast.makeText(context, location, Toast.LENGTH_SHORT).show()
                    }) {
                        Text(text = "Save")
                    }
                },
            )
        },
    ) { paddingValues ->
        ClinicManagementApp(
            modifier = Modifier.padding(paddingValues),
            textAddress = { location = it },
            locationPermissionState = locationPermissionState,
            gpsLauncher = gpsLauncher,
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ClinicManagementApp(
    modifier: Modifier = Modifier,
    textAddress: (String) -> Unit,
    locationPermissionState: MultiplePermissionsState,
    gpsLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var userInputLocation by remember { mutableStateOf("") }
    var currentLocation by remember { mutableStateOf(GeoPoint(0.0, 0.0)) }
    val zoom by remember { mutableDoubleStateOf(10.0) }

    Box(modifier = modifier.fillMaxSize()) {
        val mapView = MapView(context)
        val marker = Marker(mapView)
        Configuration.getInstance().userAgentValue = BuildConfig.LIBRARY_PACKAGE_NAME
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {
                mapView.setMultiTouchControls(true)
                mapView.controller.setZoom(10.0)
                mapView.controller.setCenter(currentLocation)
                mapView.canZoomIn()
                mapView.canZoomOut()
                mapView.minZoomLevel = 6.0
                mapView.maxZoomLevel = 20.0
                mapView.setTileSource(TileSourceFactory.MAPNIK)
                mapView
            },
            update = {
                it.controller.animateTo(
                    currentLocation,
                    zoom,
                    1000L,
                )
                marker.apply {
                    position = currentLocation
                    val addressText = getAddressText(context, currentLocation)
                    textAddress(addressText)
                    this.title = addressText
                    this.subDescription = "Current Location"
                }
                it.overlays.add(marker)
                it.overlayManager.add(
                    MapEventsOverlay(
                        object : MapEventsReceiver {
                            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                                currentLocation = p
                                return true
                            }

                            override fun longPressHelper(p: GeoPoint): Boolean {
                                return true
                            }
                        },
                    ),
                )
            },
        )
        AddressInputField(
            value = userInputLocation,
            onValueChange = { userInputLocation = it },
        ) { searchText ->
            coroutineScope.launch {
                val address = getAddressByName(context, searchText)
                if (address != null) {
                    currentLocation = GeoPoint(address.latitude, address.longitude)
                } else {
                    Toast.makeText(context, "No such place found", Toast.LENGTH_SHORT).show()
                }
            }
        }
        FloatingActionButton(
            onClick = {
                if (locationPermissionState.allPermissionsGranted) {
                    checkLocationSetting(
                        context,
                        onDisabled = { intentSenderRequest ->
                            gpsLauncher.launch(intentSenderRequest)
                        },
                        onEnabled = {
                            currentLocation = GeoPoint(it.latitude, it.longitude)
                        },
                    )
                } else {
                    locationPermissionState.launchMultiplePermissionRequest()
                }
            },
            modifier =
                Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
        ) {
            Icon(imageVector = Icons.Default.MyLocation, contentDescription = null)
        }
    }
}

@Composable
fun AddressInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
            ),
        shape = MaterialTheme.shapes.extraLarge,
        placeholder = { Text("Search here") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions =
            KeyboardActions(onSearch = {
                onSearchClick(value)
            }),
    )
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

fun getAddressByName(
    context: Context,
    locationName: String,
): Address? {
    val geocoder = Geocoder(context, Locale.getDefault())
    return geocoder.getFromLocationName(locationName, 1)?.firstOrNull()
}

fun checkLocationSetting(
    context: Context,
    onDisabled: (IntentSenderRequest) -> Unit,
    onEnabled: (Location) -> Unit,
) {
    val locationRequest =
        LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    val builder: LocationSettingsRequest.Builder =
        LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val gpsSettingTask = client.checkLocationSettings(builder.build())

    gpsSettingTask.addOnSuccessListener {
        fetchCurrentLocation(context, onEnabled)
    }
    gpsSettingTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest =
                    IntentSenderRequest
                        .Builder(exception.resolution)
                        .build()
                onDisabled(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                // ignore here
            }
        }
    }
}

private fun fetchCurrentLocation(
    context: Context,
    onLocationRetrieved: (Location) -> Unit,
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let { onLocationRetrieved(it) }
    }
}
