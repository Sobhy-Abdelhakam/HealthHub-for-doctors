package dev.sobhy.healthhubfordoctors.profilefeature.presentation.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import dev.sobhy.healthhubfordoctors.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun ProfileScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(Modifier.size(120.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_pic),
                            contentDescription = null,
                            modifier =
                                Modifier
                                    .size(120.dp)
                                    .fillMaxSize()
                                    .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier =
                                Modifier
                                    .size(40.dp)
                                    .align(Alignment.BottomEnd),
                        )
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

                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
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

                Text(
                    text = "this is text show you my bio, it is the string about me",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp),
                )
            }
        }
        item {
            HorizontalDivider(modifier = Modifier.padding(8.dp))
        }
        item {
            val state = rememberPagerState { 2 }
            val coroutineScope = rememberCoroutineScope()
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
            HorizontalPager(state = state) {
                when (it) {
                    0 -> PersonalInfo(modifier = Modifier.fillMaxSize())
                    1 -> ClinicInfo(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun PersonalInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
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
        HorizontalDivider(modifier = Modifier.padding(8.dp))
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

        HorizontalDivider(modifier = Modifier.padding(8.dp))

        Text(text = "Experience:")
        Text(text = "Contact Information:")

        HorizontalDivider(modifier = Modifier.padding(8.dp))

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
    Column(modifier = modifier.padding(16.dp)) {
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