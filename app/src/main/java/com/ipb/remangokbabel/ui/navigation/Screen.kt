package com.ipb.remangokbabel.ui.navigation

sealed class Screen(val route: String) {
    data object Splash: Screen("splash")
    data object Login: Screen("auth/login")
    data object Register: Screen("auth/register")
    data object Home: Screen("home")
    data object Order: Screen("order")
    data object Setting: Screen("setting")
    data object EditProfile: Screen("profile/edit_profile/{profileData}") {
        fun createRoute(profileData: String) = "profile/edit_profile/$profileData"
    }
    data object ManagementProduct: Screen("management_product")
    data object AddProduct: Screen("management_product/add_product")
    data object EditProduct: Screen("management_product/edit_product/{productData}") {
        fun createRoute(productData: String) = "management_product/edit_product/$productData"
    }
    data object DetailProduct: Screen("detail_product/{productData}") {
        fun createRoute(productData: String) = "detail_product/$productData"
    }
}