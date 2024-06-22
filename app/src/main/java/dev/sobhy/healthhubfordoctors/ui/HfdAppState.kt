package dev.sobhy.healthhubfordoctors.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.sobhy.healthhubfordoctors.navigation.BottomBarRoutes

@Composable
fun rememberAppState(navHostController: NavController = rememberNavController()) =
    remember(navHostController) {
        HfdAppState(navHostController)
    }

@Stable
class HfdAppState(
    private val navHostController: NavController,
) {
    private val routes = BottomBarRoutes.entries.map { it.route }

    val shouldShowBottomBar: Boolean
        @Composable get() =
            navHostController.currentBackStackEntryAsState().value?.destination?.route in routes
}
