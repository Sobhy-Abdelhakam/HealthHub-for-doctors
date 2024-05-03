package dev.sobhy.healthhubfordoctors.authfeature

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>(start = true)
@Composable
fun MainScreen() {
    Box {
        Text(text = "Main Application Screens")
    }
}
