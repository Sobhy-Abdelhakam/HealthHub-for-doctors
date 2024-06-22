package dev.sobhy.healthhubfordoctors.navigation

sealed class ScreenRoutes(val route: String) {
    data object Authentication : ScreenRoutes("/authentication")

    data object BottomBar : ScreenRoutes("/bottombar")

    data object AddClinic : ScreenRoutes("/addclinic")

    data object AvailabilityOfClinic : ScreenRoutes("/addavailability")

    data object MapScreen : ScreenRoutes("/map")

    data object ChangePassword : ScreenRoutes("/changepassword")
}
