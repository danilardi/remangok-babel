package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.ui.navigation.NavigationItem
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp


@Composable
fun BottomBar(
    navController: NavHostController,
    navigationItems: List<NavigationItem>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 16.sdp, topEnd = 16.sdp)),
        containerColor = MyStyle.colors.bgSecondary,
        contentColor = MyStyle.colors.bgPrimary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationItems.map { item ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.sdp))
                    .clickable(
//                        interactionSource = remember { MutableInteractionSource() }, indication = null
                    ) {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                    .padding(horizontal = 8.sdp, vertical = 8.sdp)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = if (currentRoute == item.screen.route) MyStyle.colors.primaryMain else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = item.title,
                    color = if (currentRoute == item.screen.route) MyStyle.colors.primaryMain else MaterialTheme.colorScheme.onSurfaceVariant
                )
                }

//                this@NavigationBar.NavigationBarItem(
//                    icon = { Icon(item.icon, contentDescription = item.title) },
//                    label = { Text(item.title) },
//                    selected = currentRoute == item.screen.route,
//                    colors = NavigationBarItemDefaults.colors(
//                        indicatorColor = MyStyle.colors.transparent,
//                    ),
//                    onClick = {
//                    }
//                )
            }
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
            screen = Screen.Setting
        ),
    )
    BottomBar(
        navController = navController,
        navigationItems = navigationItems,
    )
}