package com.ipb.remangokbabel.ui.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Auth: Screen("auth")
    object Login: Screen("auth/login")
    object Register: Screen("auth/register")
    object Home: Screen("home")
    object Order: Screen("order")
    object Setting: Screen("setting")
    object Profile: Screen("profile")
    object ListProfile: Screen("profile/list_profile")
    object AddProfile: Screen("profile/add_profile")
    object EditProfile: Screen("profile/edit_profile/{profileData}") {
        fun createRoute(profileData: String) = "profile/edit_profile/$profileData"
    }
    object SelectAddress: Screen("profile/select_address")
    object ManagementStock: Screen("management_stock")
    object DetailProduct: Screen("detail_product/{productId}") {
        fun createRoute(productId: Int) = "detail_product/$productId"
    }
    object AddOrder: Screen("add_order/{productId}") {
        fun createRoute(productId: Int) = "add_order/$productId"
    }
    object DetailOrder: Screen("detail_order/{orderData}") {
        fun createRoute(orderData: String) = "detail_order/$orderData"
    }
    object AddProduct: Screen("add_product")
    object EditProduct: Screen("edit_product/{productId}") {
        fun createRoute(productId: Int) = "edit_product/$productId"
    }
}