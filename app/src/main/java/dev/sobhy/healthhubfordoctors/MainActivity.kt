package dev.sobhy.healthhubfordoctors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sobhy.healthhubfordoctors.navigation.BottomBarRoutes
import dev.sobhy.healthhubfordoctors.navigation.NavigationGraph
import dev.sobhy.healthhubfordoctors.navigation.ScreenRoutes
import dev.sobhy.healthhubfordoctors.ui.rememberAppState
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
                    ScreenRoutes.BottomBar.route
                } else {
                    ScreenRoutes.Authentication.route
                }
            val navController = rememberNavController()
            HealthHubForDoctorsTheme {
                val appState = rememberAppState(navController)
                Scaffold(
                    bottomBar = {
                        if (appState.shouldShowBottomBar) {
                            BottomNavigation(navController)
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val bottomItems =
        listOf(
            BottomBarRoutes.SCHEDULE,
            BottomBarRoutes.CLINICS,
            BottomBarRoutes.MESSAGE,
            BottomBarRoutes.PROFILE,
        )

    val clickItemLambda =
        remember<(BottomBarRoutes) -> Unit> {
            { screen ->
                navController.navigate(screen.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(BottomBarRoutes.SCHEDULE.route) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        }
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomItems.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                icon = {
                    val icon =
                        if (selected) {
                            screen.selectedIcon
                        } else {
                            screen.unselectedIcon
                        }
                    Icon(
                        icon,
                        contentDescription = screen.route,
                    )
                },
                selected = selected,
                onClick = {
                    // to avoid recomposition of all items when click on any icon
                    clickItemLambda(screen)
                },
            )
        }
    }
}
