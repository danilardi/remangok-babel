package com.ipb.remangokbabel.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.response.ErrorResponse
import com.ipb.remangokbabel.ui.ViewModelFactory
import com.ipb.remangokbabel.ui.common.UiState
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.screen.auth.AuthViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop

@Composable
fun ProfileScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)
    var showLoading by remember { mutableStateOf(false) }
    val logoutState by viewModel.logoutState.collectAsState()

    LaunchedEffect(logoutState) {
        when (logoutState) {
            is UiState.Idle -> {
                // No action needed for Idle state
            }

            is UiState.Loading -> {
                showLoading = true
            }

            is UiState.Success -> {
                showLoading = false
                Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                paperPrefs.deleteAllData()
                navigateToAndMakeTop(navController, Screen.Auth.route)
            }

            is UiState.Error<*> -> {
                showLoading = false
                val errorResponse = (logoutState as UiState.Error<*>).errorData
                if (errorResponse is ErrorResponse) {
                    Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile Screen",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .clickable {
                    viewModel.logout()
                }
        )
    }
}