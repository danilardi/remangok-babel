package com.ipb.remangokbabel.ui.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Auth: Screen("auth")
    object Home: Screen("home")
    object Order: Screen("order")
    object Profile: Screen("profile")

}