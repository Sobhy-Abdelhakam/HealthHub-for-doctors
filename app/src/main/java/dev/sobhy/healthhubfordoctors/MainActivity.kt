package dev.sobhy.healthhubfordoctors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import dagger.hilt.android.AndroidEntryPoint
import dev.sobhy.healthhubfordoctors.ui.theme.HealthHubForDoctorsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val isLoggedIn = mainViewModel.isLoggedIn.collectAsState()
            val startDestination =
                if (isLoggedIn.value != null) {
                    ProfileScreenDestination
                } else {
                    LoginScreenDestination
                }

            HealthHubForDoctorsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DestinationsNavHost(
                        startRoute = startDestination,
                        navGraph = NavGraphs.root,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
