package com.ipb.remangokbabel.ui.screen.profile

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
//        coroutineScope.launch {
//            viewModel.logoutResponse.collect {
//                Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
//                paperPrefs.deleteAllData()
//                navigateToAndMakeTop(navController, Screen.Login.route)
//            }
//        }
//        coroutineScope.launch {
//            viewModel.showLoading.collect {
//                showLoading = it
//            }
//        }
//        coroutineScope.launch {
//            viewModel.errorResponse.collect { errorResponse ->
//                Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
//                if (errorResponse.message == "token anda tidak valid") {
//                    paperPrefs.deleteAllData()
//                    navigateToAndMakeTop(navController, Screen.Login.route)
//                }
//            }
//        }
    }

    viewModel.logoutState.collectAsState().value?.let {
        Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
        paperPrefs.deleteAllData()
        navigateToAndMakeTop(navController, Screen.Login.route)
    }

    viewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    viewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            if (it.message == "token anda tidak valid") {
                paperPrefs.deleteAllData()
                navigateToAndMakeTop(navController, Screen.Login.route)
            }
        }
        viewModel.clearError()
    }

    Scaffold(
        topBar = {
            AppTopBar(title = "Profile")
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MyStyle.colors.bgWhite)
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            MenuItem(
                title = "Profil",
                onClick = {
                },
                modifier = Modifier
                    .padding(top = 2.sdp)
            )
            MenuItem(
                title = "Bantuan",
                onClick = {
                    val url =
                        "https://drive.google.com/drive/folders/1-yfATreiuyU7REd_dQRorx0heRbzO1-q"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .padding(top = 2.sdp)
            )
            MenuItem(
                title = "Keluar",
                onClick = {
                    viewModel.logout()
                },
                modifier = Modifier
                    .padding(top = 2.sdp)
            )
        }
    }
}

@Composable
fun MenuItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(MyStyle.colors.bgWhite)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = 16.sdp, vertical = 16.sdp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.sdp)
        )
    }
    HorizontalDivider()
}