package dev.sobhy.healthhubfordoctors.profilefeature.presentation.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import dev.sobhy.healthhubfordoctors.R
import dev.sobhy.healthhubfordoctors.navigation.ScreenRoutes

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    if (viewModel.isLoggedOut) {
        navController.popBackStack()
        navController.navigate(ScreenRoutes.Authentication.route)
    }
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        item {
            PhotoNameSpecialityRateSection(
                imageUri = state.image,
                imageChanged = {
                    viewModel.onEvent(ProfileUiEvent.ChangeProfileImage(it))
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            )
        }
        item {
            ViewsBookingNumberSection(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            )
        }
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "account settings",
                        modifier =
                            Modifier
                                .padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }

                Text(
                    text = "Account Settings",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f).padding(8.dp),
                )
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
            }
        }
        item {
            Row(
                modifier =
                    Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable {
                        navController.navigate(
                            ScreenRoutes.ClinicList.route,
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card {
                    Icon(
                        imageVector = Icons.Default.House,
                        contentDescription = "account settings",
                        modifier =
                            Modifier
                                .padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                Text(
                    text = "Clinic Settings",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
            }
        }
        item {
            Row(
                modifier =
                    Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable {
                        navController.navigate(ScreenRoutes.ChangePassword.route)
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "account settings",
                        modifier =
                            Modifier
                                .padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                Text(
                    text = "Change Password",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
            }
        }
        item {
            Button(
                onClick = {
                    viewModel.onEvent(ProfileUiEvent.Logout)
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ),
            ) {
                Text(text = "Logout")
            }
        }

//        item {
//            Text(
//                text = "this is text show you my bio, it is the string about me",
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.padding(8.dp),
//            )
//        }
//        item {
//            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//        }
//        item {
//            val pagerState = rememberPagerState { 2 }
//            val coroutineScope = rememberCoroutineScope()
//            TapRowSection(pagerState, coroutineScope)
//            HorizontalPagerSection(pagerState)
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoNameSpecialityRateSection(
    imageUri: Uri?,
    imageChanged: (Uri?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = {
                it?.let {
                    imageChanged(it)
                }
            },
        )
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(120.dp)) {
            Image(
                painter =
                    if (imageUri == null) {
                        painterResource(id = R.drawable.doctor)
                    } else {
                        rememberAsyncImagePainter(imageUri)
                    },
                contentDescription = null,
                modifier =
                    Modifier
                        .size(120.dp)
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape,
                        ),
                contentScale = ContentScale.Crop,
            )
            IconButton(
                onClick = {
                    if (imageUri == null) {
                        galleryLauncher.launch("image/*")
                    } else {
                        showBottomSheet = true
                    }
                },
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .size(45.dp),
                colors =
                    IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        disabledContentColor = MaterialTheme.colorScheme.primary,
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add Photo",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
            ) {
                Text(
                    text = "Profile photo",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Column(
                        modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        IconButton(
                            onClick = {
                                galleryLauncher.launch("image/*")
                                showBottomSheet = false
                            },
                            modifier =
                                Modifier
                                    .size(70.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = CircleShape,
                                    ),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Photo,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                        Text(text = "Gallery", modifier = Modifier.padding(8.dp))
                    }
                    Column(
                        modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        IconButton(
                            onClick = {
                                imageChanged(null)
                                showBottomSheet = false
                            },
                            modifier =
                                Modifier
                                    .size(70.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = CircleShape,
                                    ),
                            colors =
                                IconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.error,
                                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    disabledContentColor = MaterialTheme.colorScheme.secondary,
                                ),
                            enabled = imageUri != null,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = "Delete",
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "Sobhy Abdel Hakam",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Female Doctor",
                style = MaterialTheme.typography.titleMedium,
            )
            Row {
                repeat(5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (it < 3) Color.Yellow else Color.Gray,
                        modifier = Modifier.padding(2.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun ViewsBookingNumberSection(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Column {
                Text(
                    text = "0",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Text(text = "Profile Views", modifier = Modifier.padding(8.dp))
            }
            VerticalDivider()
            Column {
                Text(
                    text = "0",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Text(text = "Bookings", modifier = Modifier.padding(8.dp))
            }
        }
    }
}
