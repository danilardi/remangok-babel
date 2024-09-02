package com.ipb.remangokbabel.utils

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.ipb.remangokbabel.model.response.ErrorResponse
import com.ipb.remangokbabel.ui.common.UiState
import retrofit2.HttpException

fun navigateTo(navController: NavHostController, route: String) {
    navController.navigate(route)
}

fun navigateToAndMakeTop(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            inclusive = true
        }
    }
}

fun handleException(exception: Exception): UiState.Error<ErrorResponse> {
    val errorResponse = when (exception) {
        is HttpException -> parseError(exception.response()?.errorBody()?.string())
        else -> ErrorResponse("An unknown error occurred") // Return a default ErrorResponse object
    }
    return UiState.Error(errorResponse)
}

fun parseError(json: String?): ErrorResponse {
    return try {
        if (json != null) {
            val gson = Gson()
            gson.fromJson(json, ErrorResponse::class.java)
        } else {
            ErrorResponse("An unexpected error occurred") // Return default ErrorResponse if JSON is null
        }
    } catch (e: Exception) {
        ErrorResponse("Failed to parse error message") // Return default ErrorResponse if parsing fails
    }
}