package com.ipb.remangokbabel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.screen.auth.LoginScreen
import com.ipb.remangokbabel.ui.screen.auth.RegisterScreen
import com.ipb.remangokbabel.ui.screen.basic.SplashScreen

@Composable
fun MainApp(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    navController = navController,
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen()
            }
            composable(Screen.Login.route) {
                LoginScreen()
            }
        }
    }
}