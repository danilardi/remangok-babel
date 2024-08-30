package com.ipb.remangokbabel.ui.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Register: Screen("basic/register")
    object Login: Screen("basic/login")
    object Home: Screen("home")
}