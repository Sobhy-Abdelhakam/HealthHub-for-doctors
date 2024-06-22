package dev.sobhy.healthhubfordoctors.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword.ForgetPasswordScreen
import dev.sobhy.healthhubfordoctors.authfeature.presentation.login.LoginScreen
import dev.sobhy.healthhubfordoctors.authfeature.presentation.register.RegisterScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(startDestination = AuthenticationRoutes.LOGIN.route, route = ScreenRoutes.Authentication.route) {
        composable(route = AuthenticationRoutes.LOGIN.route) {
            LoginScreen(navController = navController)
        }
        composable(route = AuthenticationRoutes.REGISTER.route) {
            RegisterScreen(navController)
        }
        composable(route = AuthenticationRoutes.FORGOTPASSWORD.route) {
            ForgetPasswordScreen()
        }
    }
}
