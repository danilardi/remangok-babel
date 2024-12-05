package com.ipb.remangokbabel

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BottomBar
import com.ipb.remangokbabel.ui.navigation.NavigationItem
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.screen.auth.LoginScreen
import com.ipb.remangokbabel.ui.screen.auth.RegisterScreen
import com.ipb.remangokbabel.ui.screen.basic.OnBoardingScreen
import com.ipb.remangokbabel.ui.screen.basic.SplashScreen
import com.ipb.remangokbabel.ui.screen.home.HomeScreen
import com.ipb.remangokbabel.ui.screen.product.AddProductScreen
import com.ipb.remangokbabel.ui.screen.product.DetailProductScreen
import com.ipb.remangokbabel.ui.screen.product.ManagementProductScreen
import com.ipb.remangokbabel.ui.screen.profile.EditProfileScreen
import com.ipb.remangokbabel.ui.screen.profile.SettingScreen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay

@Composable
fun MainApp(
    navController: NavHostController = rememberNavController(),
    enableEdgeToEdge: (statusBar: SystemBarStyle, navbar: SystemBarStyle) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val startDestination by remember { mutableStateOf(Screen.Splash.route) }

    val context = LocalContext.current
    val paperPref = PaperPrefs(context)

    val doubleBackToExitPressedOnce = remember { mutableStateOf(false) }

    val navigationItems = if (paperPref.getRole() == "admin") listOf(
        NavigationItem(
            title = "Manajemen",
            icon = Icons.Default.AllInbox,
            screen = Screen.ManagementProduct
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.AccountCircle,
            screen = Screen.Setting
        ),
    ) else listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = "Manajemen",
            icon = Icons.Default.AllInbox,
            screen = Screen.ManagementProduct
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.AccountCircle,
            screen = Screen.Setting
        ),
    )

    fun setTopNavBarColor(topColor: Color, bottomColor: Color) {
        enableEdgeToEdge(
            SystemBarStyle.light(topColor.toArgb(), topColor.toArgb()),
            SystemBarStyle.light(bottomColor.toArgb(), bottomColor.toArgb()),
        )
    }

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            Screen.Splash.route -> setTopNavBarColor(
                MyStyle.colors.bgSplash,
                MyStyle.colors.bgSplash
            )

            Screen.Home.route -> setTopNavBarColor(
                MyStyle.colors.bgWhite,
                MyStyle.colors.bgSecondary
            )

            Screen.ManagementProduct.route -> setTopNavBarColor(
                MyStyle.colors.bgWhite,
                MyStyle.colors.bgSecondary
            )

            Screen.Setting.route -> setTopNavBarColor(
                MyStyle.colors.bgWhite,
                MyStyle.colors.bgSecondary
            )

            else -> setTopNavBarColor(MyStyle.colors.bgWhite, MyStyle.colors.bgWhite)
        }
    }

    LaunchedEffect(doubleBackToExitPressedOnce.value) {
        if (doubleBackToExitPressedOnce.value) {
            delay(2000)
            doubleBackToExitPressedOnce.value = false
        }
    }

    BackHandler {
        if ((currentRoute == Screen.Login.route || currentRoute == Screen.Home.route) && !doubleBackToExitPressedOnce.value) {
            Toast.makeText(context, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
            doubleBackToExitPressedOnce.value = true
        } else if (doubleBackToExitPressedOnce.value) {
            (context as ComponentActivity).finish()
        } else {
            navController.popBackStack()
        }
    }

    Scaffold(
        bottomBar = {
            if (
                currentRoute == Screen.Home.route ||
                currentRoute == Screen.ManagementProduct.route ||
                currentRoute == Screen.Setting.route
            ) {
                BottomBar(
                    navController = navController,
                    navigationItems = navigationItems
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(MyStyle.colors.bgWhite),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = if (innerPadding.calculateBottomPadding() > 16.sdp) innerPadding.calculateBottomPadding() - 16.sdp else 0.sdp
            )
        ) {
            composable(Screen.Splash.route) {
                SplashScreen() {
                    val destination = if (paperPref.getAccessToken().isEmpty())
                        Screen.Onboarding.route
                    else if (paperPref.getRole() == "admin")
                        Screen.ManagementProduct.route
                    else
                        Screen.Home.route
                    navigateToAndMakeTop(navController, destination)
                }
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    navController = navController,
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navController = navController,
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController
                )
            }
            composable(
                route = Screen.DetailProduct.route,
                arguments = listOf(navArgument("productData") { type = NavType.StringType })
            ) {
                val jsonProductData = it.arguments?.getString("productData") ?: ""
                val productData = Gson().fromJson(jsonProductData, ProductItem::class.java)
                DetailProductScreen(
                    navController = navController,
                    product = productData
                )
            }
            composable(Screen.ManagementProduct.route) {
                ManagementProductScreen(
                    navController = navController,
                )
            }
            composable(Screen.AddProduct.route) {
                AddProductScreen(
                    navController = navController
                )
            }
            composable(
                route = Screen.EditProduct.route,
                arguments = listOf(navArgument("productData") { type = NavType.StringType })
            ) {
                val jsonProductData = it.arguments?.getString("productData") ?: ""
                val productData = Gson().fromJson(jsonProductData, ProductItem::class.java)
                AddProductScreen(
                    navController = navController,
                    product = productData
                )
            }
            composable(Screen.Setting.route) {
                SettingScreen(
                    navController = navController,
                )
            }
            composable(
                route = Screen.EditProfile.route,
                arguments = listOf(navArgument("profileData") { type = NavType.StringType })
            ) {
                val jsonProfileData = it.arguments?.getString("profileData") ?: ""
                val profileData = Gson().fromJson(jsonProfileData, ProfilesItem::class.java)
                EditProfileScreen(
                    navController = navController,
                    profileData = profileData
                )
            }
            composable(
                route = Screen.Onboarding.route,
            ) {
                OnBoardingScreen(
                    navController = navController,
                )
            }
        }
    }
}

