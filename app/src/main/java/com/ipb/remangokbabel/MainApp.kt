package com.ipb.remangokbabel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.ui.components.common.BottomBar
import com.ipb.remangokbabel.ui.navigation.NavigationItem
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.screen.auth.AuthScreen
import com.ipb.remangokbabel.ui.screen.basic.SplashScreen
import com.ipb.remangokbabel.ui.screen.home.HomeScreen
import com.ipb.remangokbabel.ui.screen.order.OrderScreen
import com.ipb.remangokbabel.ui.screen.profile.ProfileScreen
import com.ipb.remangokbabel.utils.navigateToAndMakeTop

@Composable
fun MainApp(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var startDestination by remember { mutableStateOf(Screen.Splash.route) }

    val paperPref = PaperPrefs(context = LocalContext.current)

    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = "Pesanan",
            icon = Icons.AutoMirrored.Filled.StickyNote2,
            screen = Screen.Order
        ),
        NavigationItem(
            title = "Profil",
            icon = Icons.Default.AccountCircle,
            screen = Screen.Profile
        ),
    )

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Splash.route && currentRoute != Screen.Auth.route) {
                BottomBar(
                    navController = navController,
                    navigationItems = navigationItems
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen() {
                    val destination = if (paperPref.getAccessToken().isEmpty()) Screen.Auth.route else Screen.Home.route
                    startDestination = destination
                    navigateToAndMakeTop(navController, destination)
                }
            }
            composable(Screen.Auth.route) {
                AuthScreen(
                    navController = navController,
                )
            }
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Order.route) {
                OrderScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = navController,
                )
            }
        }
    }
}

