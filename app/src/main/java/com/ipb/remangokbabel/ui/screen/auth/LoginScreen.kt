package com.ipb.remangokbabel.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.AuthViewModel
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        email = paperPrefs.getEmailSaved()
        if (BuildConfig.DEBUG) {
            email = "yulia.sari@jualanmail.com"
            password = "yulia303"
//            email = "albarrazikrillah1405@gmail.com"
//            password = "P_assword001"
        }
    }

    authViewModel.loginState.collectAsState().value?.let {
        LaunchedEffect(Unit) {
            val data = it.dataLoginResponse
            paperPrefs.setAccessToken(data.accessToken)
            paperPrefs.setRefreshToken(data.refreshToken)
            paperPrefs.setRole(data.roleId)
            paperPrefs.setEmailSaved(email)
            profileViewModel.getProfile()
        }
    }

    profileViewModel.getProfileState.collectAsState().value?.let {
        LaunchedEffect(Unit) {
            paperPrefs.setProfile(it.data.profiles)
            Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
            val destination =
                if (paperPrefs.getRole() == "admin") Screen.ManagementProduct.route else Screen.Home.route
            profileViewModel.clearProfileState()
            navigateToAndMakeTop(navController, destination)
        }
    }

    authViewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
        authViewModel.clearError()
    }

    authViewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    profileViewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    profileViewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            if (it.message == "token anda tidak valid") {
                paperPrefs.deleteAllData()
            }
        }
        profileViewModel.clearError()
    }
    Scaffold(
        topBar = {
            BackTopBar(title = "Masuk", onClickBackButton = {
                navigateToBack(navController)
            })
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.sdp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Selamat Datang\nAyo Eksplor Remangok Babel 🦀",
                style = MyStyle.typography.baseBold,
                fontSize = 18.ssp,
                color = MyStyle.colors.textBlack,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 50.sdp)
                    .fillMaxWidth()
            )
            InputLayout(
                title = "Email",
                value = email,
                hint = "Masukkan Email",
                modifier = Modifier.padding(top = 20.sdp)
            ) {
                email = it
            }
            InputLayout(
                title = "Kata Sandi",
                value = password,
                hint = "Silahkan masukkan kata sandi anda",
                modifier = Modifier.padding(top = 16.sdp),
                isPassword = true
            ) {
                password = it
            }
            ButtonCustom(
                text = "Masuk",
                enabled = email.isNotEmpty() && password.isNotEmpty(),
                modifier = Modifier.padding(top = 24.sdp)
            ) {
                authViewModel.login(LoginRequest(email, password))
            }
            Row(
                modifier = Modifier
                    .padding(top = 16.sdp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Belum punya akun?", style = MyStyle.typography.baseNormal)
                Text(text = "Daftar Disini",
                    style = MyStyle.typography.baseBold,
                    color = MyStyle.colors.primaryMain,
                    fontSize = 12.ssp,
                    modifier = Modifier
                        .padding(start = 6.sdp)
                        .clickable {
                            navigateTo(navController, Screen.Register.route)
                        })
            }
        }
    }

}
