package dev.sobhy.healthhubfordoctors.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.sobhy.healthhubfordoctors.chatfeature.ChatListScreen
import dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.AddClinicScreen
import dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.availability.AvailabilityScreen
import dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic.map.ClinicAddressScreen
import dev.sobhy.healthhubfordoctors.clinicfeature.presentation.clinics.ClinicListScreen
import dev.sobhy.healthhubfordoctors.profilefeature.presentation.profile.ProfileScreen
import dev.sobhy.healthhubfordoctors.profilefeature.presentation.updatepassword.ChangePasswordScreen
import dev.sobhy.healthhubfordoctors.schedulefeature.ScheduleScreen

fun NavGraphBuilder.appGraph(navController: NavHostController) {
    navigation(startDestination = BottomBarRoutes.SCHEDULE.route, route = ScreenRoutes.BottomBar.route) {
        composable(route = BottomBarRoutes.SCHEDULE.route) {
            ScheduleScreen()
        }
        composable(route = BottomBarRoutes.CLINICS.route) {
            ClinicListScreen(navController = navController)
        }
        composable(route = BottomBarRoutes.MESSAGE.route) {
            ChatListScreen()
        }
        composable(route = BottomBarRoutes.PROFILE.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = ScreenRoutes.AddClinic.route) {
            AddClinicScreen(navController = navController)
        }
        composable(
            route = "${ScreenRoutes.AvailabilityOfClinic.route}/{clinic_id}",
            arguments =
                listOf(
                    navArgument("clinic_id") {
                        type = NavType.IntType
                    },
                ),
        ) {
            val clinicId = it.arguments?.getInt("clinic_id") ?: 0
            AvailabilityScreen(navController = navController, clinicId = clinicId)
        }
        composable(route = ScreenRoutes.MapScreen.route) {
            ClinicAddressScreen(navController = navController)
        }
        composable(route = ScreenRoutes.ChangePassword.route) {
            ChangePasswordScreen()
        }
    }
}
