package com.ipb.remangokbabel

import androidx.activity.SystemBarStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.response.DetailOrderedItem
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BottomBar
import com.ipb.remangokbabel.ui.navigation.NavigationItem
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.screen.auth.AuthScreen
import com.ipb.remangokbabel.ui.screen.auth.LoginScreen
import com.ipb.remangokbabel.ui.screen.auth.RegisterScreen
import com.ipb.remangokbabel.ui.screen.basic.SplashScreen
import com.ipb.remangokbabel.ui.screen.home.HomeScreen
import com.ipb.remangokbabel.ui.screen.order.OrderDetailScreen
import com.ipb.remangokbabel.ui.screen.order.OrderScreen
import com.ipb.remangokbabel.ui.screen.product.AddProductScreen
import com.ipb.remangokbabel.ui.screen.product.DetailProductScreen
import com.ipb.remangokbabel.ui.screen.product.ManagementProductScreen
import com.ipb.remangokbabel.ui.screen.product.OrderProductScreen
import com.ipb.remangokbabel.ui.screen.profile.AddProfileScreen
import com.ipb.remangokbabel.ui.screen.profile.ListProfileScreen
import com.ipb.remangokbabel.ui.screen.profile.SelectAddressScreen
import com.ipb.remangokbabel.ui.screen.profile.SettingScreen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.BaseViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop

@Composable
fun MainApp(
    navController: NavHostController = rememberNavController(),
    viewModel: BaseViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    enableEdgeToEdge: (statusBar:SystemBarStyle, navbar:SystemBarStyle) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val startDestination by remember { mutableStateOf(Screen.Splash.route) }

    val context = LocalContext.current
    val paperPref = PaperPrefs(context)

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
            Screen.Splash.route -> setTopNavBarColor(MyStyle.colors.bgSplash, MyStyle.colors.bgSplash)
            Screen.Home.route -> setTopNavBarColor(MyStyle.colors.bgWhite, MyStyle.colors.bgSecondary)
            Screen.Order.route -> setTopNavBarColor(MyStyle.colors.bgWhite, MyStyle.colors.bgSecondary)
            Screen.Setting.route -> setTopNavBarColor(MyStyle.colors.bgWhite, MyStyle.colors.bgSecondary)
            else -> setTopNavBarColor(MyStyle.colors.bgWhite, MyStyle.colors.bgWhite)
        }
    }

    Scaffold(
        bottomBar = {
            if (
                currentRoute == Screen.Home.route ||
                currentRoute == Screen.Order.route ||
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
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen() {
                    val destination = if (paperPref.getAccessToken()
                            .isEmpty()
                    ) Screen.Login.route else Screen.Home.route
                    navigateToAndMakeTop(navController, destination)
                }
            }
            composable(Screen.Auth.route) {
                AuthScreen(
                    navController = navController,
                )
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
            composable(Screen.Order.route) {
                OrderScreen(
                    navController = navController
                )
            }
            composable(
                route = Screen.DetailOrder.route,
                arguments = listOf(navArgument("orderData") { type = NavType.StringType })
            ) {
                val jsonOrderData = it.arguments?.getString("orderData") ?: ""
                val orderData = Gson().fromJson(jsonOrderData, DetailOrderedItem::class.java)
                OrderDetailScreen(
                    navController = navController,
                    orderData = orderData
                )
            }
            composable(Screen.Setting.route) {
                SettingScreen(
                    navController = navController,
                )
            }
            composable(Screen.ListProfile.route) {
                ListProfileScreen(
                    navController = navController
                )
            }
            composable(Screen.AddProfile.route) {
                AddProfileScreen(
                    navController = navController
                )
            }
            composable(
                route = Screen.EditProfile.route,
                arguments = listOf(navArgument("profileData") { type = NavType.StringType })
            ) {
                val jsonProfileData = it.arguments?.getString("profileData") ?: ""
                val profileData = Gson().fromJson(jsonProfileData, ProfilesItem::class.java)
                AddProfileScreen(
                    data = profileData,
                    navController = navController,
                )
            }
            composable(Screen.SelectAddress.route) {
                SelectAddressScreen(
                    navController = navController
                )
            }

            composable(Screen.ManagementStock.route) {
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
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) {
                val productId = it.arguments?.getInt("productId") ?: -1
                AddProductScreen(
                    navController = navController,
                    productId = productId
                )
            }
            composable(
                route = Screen.DetailProduct.route,
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) {
                val productId = it.arguments?.getInt("productId") ?: -1
                DetailProductScreen(
                    navController = navController,
                    productId = productId
                )
            }
            composable(
                route = Screen.AddOrder.route,
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) {
                val productId = it.arguments?.getInt("productId") ?: -1
                OrderProductScreen(
                    navController = navController,
                    productId = productId
                )
            }
        }
    }
}

