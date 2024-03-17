package dev.sobhy.healthhubfordoctors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.sobhy.healthhubfordoctors.authfeature.presentation.login.LoginScreen
import dev.sobhy.healthhubfordoctors.authfeature.presentation.register.RegisterScreen
import dev.sobhy.healthhubfordoctors.ui.theme.HealthHubForDoctorsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            HealthHubForDoctorsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable("login") {
                            LoginScreen(onNavigateToRegister = { navController.navigate("register") })
                        }
                        composable("register") {
                            RegisterScreen()
                        }
                    }
                }
            }
        }
    }
}
