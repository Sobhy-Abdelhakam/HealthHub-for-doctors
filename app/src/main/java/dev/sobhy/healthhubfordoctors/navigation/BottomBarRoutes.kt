package dev.sobhy.healthhubfordoctors.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomBarRoutes(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
) {
    HOME(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = "/home",
    ),
    SEARCH(
        selectedIcon = Icons.Filled.Schedule,
        unselectedIcon = Icons.Outlined.Schedule,
        route = "/search",
    ),
    CHATBOT(
        selectedIcon = Icons.Filled.Headphones,
        unselectedIcon = Icons.Outlined.Headphones,
        route = "/chatbot",
    ),
    MESSAGE(
        selectedIcon = Icons.Filled.Message,
        unselectedIcon = Icons.Outlined.Message,
        route = "/message",
    ),
    PROFILE(
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = "/profile",
    ),
}
