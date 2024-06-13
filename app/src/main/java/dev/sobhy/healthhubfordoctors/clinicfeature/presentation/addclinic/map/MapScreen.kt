package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.map

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.ZoomButtonVisibility
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun ClinicAddressScreen() {
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
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Save")
                    }
                },
            )
        },
    ) {
        ClinicManagementApp(modifier = Modifier.padding(it))
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ClinicManagementApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var inputAddress by remember { mutableStateOf("") }
    var location by remember { mutableStateOf(GeoPoint(0.0, 0.0)) }
    val zoom by remember { mutableDoubleStateOf(10.0) }
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val coroutineScope = rememberCoroutineScope()
    val settingResultRequest =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
        ) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                Log.d("appDebug", "Accepted")
            } else {
                Log.d("appDebug", "Denied")
            }
        }
//    val cameraState =
//        rememberCameraState {
//            geoPoint = location
//            zoom = 8.0
//        }
//    val markerState = rememberMarkerState(geoPoint = location)

    LaunchedEffect(Unit) {
        if (locationPermissionState.status.isGranted) {
            fetchCurrentLocation(context) { loc ->
                location = GeoPoint(loc.latitude, loc.longitude)
//                cameraState.geoPoint = location
//                markerState.geoPoint = location
            }
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }
    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }
    SideEffect {
        mapProperties =
            mapProperties.copy(
                isTilesScaledToDpi = true,
                tileSources = TileSourceFactory.MAPNIK,
                isEnableRotationGesture = true,
                zoomButtonVisibility = ZoomButtonVisibility.SHOW_AND_FADEOUT,
            )
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (locationPermissionState.status.isGranted) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = {
                    val mapView = MapView(it)
                    mapView.setMultiTouchControls(true)
                    mapView.controller.setZoom(10.0)
                    mapView.controller.setCenter(location)
                    mapView.canZoomIn()
                    mapView.canZoomOut()
                    mapView.minZoomLevel = 6.0
                    mapView.maxZoomLevel = 20.0
                    mapView.setTileSource(TileSourceFactory.MAPNIK)
                    val marker =
                        Marker(mapView).apply {
                            position = location
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }
                    mapView.overlays.add(marker)
                    mapView
                },
                update = {
                    it.controller.animateTo(
                        location,
                        zoom,
                        1000L,
                    )
                },
            )
//            OpenStreetMap(
//                modifier = Modifier.fillMaxWidth(),
//                cameraState = cameraState,
//                properties = mapProperties,
//                onMapClick = {
//                    location = it
//                    markerState.geoPoint = it
//                },
//            ) {
//                Marker(
//                    state = markerState,
//                )
//            }
            AddressInputField(
                value = inputAddress,
                onValueChange = {
                    inputAddress = it
                },
            ) { searchText ->
                coroutineScope.launch {
                    val address = getAddressByName(context, searchText)
                    if (address != null) {
                        location = GeoPoint(address.latitude, address.longitude)
//                        cameraState.geoPoint = location
//                        cameraState.zoom = 10.0
//                        markerState.geoPoint = location
                    } else {
                        Toast.makeText(context, "No such place found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    checkLocationSetting(
                        context,
                        onDisabled = { intentsenderRequest ->
                            settingResultRequest.launch(intentsenderRequest)
                        },
                        onEnabled = {
                            location = GeoPoint(it.latitude, it.longitude)
                            Toast.makeText(context, "$location", Toast.LENGTH_SHORT).show()
//                            cameraState.geoPoint = location
//                            cameraState.zoom = 10.0
//                            markerState.geoPoint = location
                        },
                    )
                },
                modifier =
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),
            ) {
                Icon(imageVector = Icons.Default.MyLocation, contentDescription = null)
            }
        } else {
            PermissionRequestContent(locationPermissionState = locationPermissionState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
            TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequestContent(locationPermissionState: PermissionState) {
    Column(modifier = Modifier.padding(16.dp)) {
        val textToShow =
            if (locationPermissionState.status.shouldShowRationale) {
                "The location is important for this app. Please grant the permission."
            } else {
                "Location permission required for this feature to be available. Please grant the permission"
            }
        Text(textToShow)
        Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
            Text("Request permission")
        }
    }
}

fun getAddressByName(
    context: Context,
    locationName: String,
): Address? {
    val geocoder = Geocoder(context, Locale.getDefault())
//    var geoResults = geocoder.getFromLocationName(locationName, 1)
    return geocoder.getFromLocationName(locationName, 1)?.firstOrNull()
}

// call this function on button click
fun checkLocationSetting(
    context: Context,
    onDisabled: (IntentSenderRequest) -> Unit,
    onEnabled: (Location) -> Unit,
) {
    val locationRequest =
        LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val builder: LocationSettingsRequest.Builder =
        LocationSettingsRequest
            .Builder()
            .addLocationRequest(locationRequest)

    val gpsSettingTask: Task<LocationSettingsResponse> =
        client.checkLocationSettings(builder.build())

    gpsSettingTask.addOnSuccessListener {
        Toast.makeText(context, "success listener", Toast.LENGTH_SHORT).show()
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
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let { onLocationRetrieved(it) }
    }
}
