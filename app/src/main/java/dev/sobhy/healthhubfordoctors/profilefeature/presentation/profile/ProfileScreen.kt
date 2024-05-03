package dev.sobhy.healthhubfordoctors.profilefeature.presentation.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import dev.sobhy.healthhubfordoctors.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Destination<RootGraph>(start = true)
@Composable
fun ProfileScreen() {
    val viewModel: ProfileViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        item {
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = {
                    it?.let {
                        viewModel.onEvent(ProfileUiEvent.ChangeProfileImage(it))
                    }
                },
            )
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
            Text(
                text = "this is text show you my bio, it is the string about me",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp),
            )
        }
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }
        item {
            val pagerState = rememberPagerState { 2 }
            val coroutineScope = rememberCoroutineScope()
            TapRowSection(pagerState, coroutineScope)
            HorizontalPagerSection(pagerState)
        }
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

@Composable
private fun TapRowSection(
    state: PagerState,
    coroutineScope: CoroutineScope,
) {
    TabRow(selectedTabIndex = state.currentPage) {
        Tab(
            selected = state.currentPage == 0,
            onClick = {
                coroutineScope.launch {
                    state.animateScrollToPage(0)
                }
            },
        ) {
            Icon(imageVector = Icons.Default.Info, contentDescription = null)
            Text(text = "Personal Info")
        }
        Tab(
            selected = state.currentPage == 1,
            onClick = {
                coroutineScope.launch {
                    state.animateScrollToPage(1)
                }
            },
        ) {
            Icon(imageVector = Icons.Default.House, contentDescription = null)
            Text(text = "Clinic Info")
        }
    }
}

@Composable
private fun HorizontalPagerSection(state: PagerState) {
    HorizontalPager(state = state) {
        when (it) {
            0 -> PersonalInfo(modifier = Modifier.fillMaxSize())
            1 -> ClinicInfo(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun PersonalInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = "Basic info",
            style = MaterialTheme.typography.titleMedium,
        )
        Row {
            Text(text = "Gender: ")
            Text(text = "Male", fontWeight = FontWeight.Bold)
        }
        Row {
            Text(text = "Birthday: ")
            Text(text = "25 October 2000", fontWeight = FontWeight.Bold)
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = "Education",
            style = MaterialTheme.typography.titleMedium,
        )
        Row {
            Text(text = "Medical Speciality: ")
            Text(text = "Hematology", fontWeight = FontWeight.Bold)
        }

        Row {
            Text(text = "Education: ")
            Text(
                text = "Faculty of Medicine, Al-Azhar University",
                fontWeight = FontWeight.Bold,
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(text = "Experience:")
        Text(text = "Contact Information:")

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "Contact Info",
            style = MaterialTheme.typography.titleMedium,
        )
        Row {
            Text(text = "Phone: ")
            Text(text = "01121015492", fontWeight = FontWeight.Bold)
        }
        Row {
            Text(text = "Email: ")
            Text(text = "sobhy.abdelhakam@gmail.com", fontWeight = FontWeight.Bold)
        }
        Row {
            Text(text = "WhatsApp: ")
            Text(text = "01121015492", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ClinicInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Clinic Name:")
        Text(text = "Clinic Number:")
        Text(text = "Clinic Photos:")
        Text(text = "Clinic Address:")
        Text(text = "Examination:")
        Text(text = "Follow-up")
        Text(text = "Clinic Description:")
        Text(text = "Clinic Rating:")
    }
}
