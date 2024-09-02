package com.ipb.remangokbabel.ui.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.ui.navigation.NavigationItem
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle


@Composable
fun BottomBar(
    navController: NavHostController,
    navigationItems: List<NavigationItem>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MyStyle.colors.backgroundSecondary,
        contentColor = MyStyle.colors.backgroundPrimary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationItems.map { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MyStyle.colors.primaryFocus,
                ),
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun BottomBarPreview() {
    val navController: NavHostController = rememberNavController()
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
    BottomBar(
        navController = navController,
        navigationItems = navigationItems,
    )
}