package dev.sobhy.healthhubfordoctors.schedulefeature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ScheduleScreen(scheduleViewModel: ScheduleViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Schedule Screen", modifier = Modifier.align(Alignment.Center))
    }
}
