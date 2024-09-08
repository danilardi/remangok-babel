package com.ipb.remangokbabel.ui.navigation

import com.ipb.remangokbabel.model.response.ProfilesItem

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Auth: Screen("auth")
    object Home: Screen("home")
    object Order: Screen("order")
    object Profile: Screen("profile")
    object ListProfile: Screen("profile/list_profile")
    object AddProfile: Screen("profile/add_profile")
    object EditProfile: Screen("profile/edit_profile/{addressId}") {
        fun createRoute(item: ProfilesItem) = "profile/edit_profile/$item"
    }
    object SelectAddress: Screen("profile/select_address")
    object ManagementStock: Screen("management_stock")
    object AddProduct: Screen("add_product")
    object EditProduct: Screen("edit_product/{productId}") {
        fun createRoute(productId: Int) = "edit_product/$productId"
    }
}