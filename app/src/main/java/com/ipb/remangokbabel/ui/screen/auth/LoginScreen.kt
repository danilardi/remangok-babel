package com.ipb.remangokbabel.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.AuthViewModel
import com.ipb.remangokbabel.utils.navigateTo
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var showLoading by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (BuildConfig.DEBUG) {
//            email = "sumbul2"
//            password = "password"
            email = "medomeckz"
            password = "password001"
        }
        coroutineScope.launch {
            viewModel.loginResponse.collect { loginResponse ->
                paperPrefs.setAccessToken(loginResponse.dataLoginResponse.accessToken)
                paperPrefs.setRefreshToken(loginResponse.dataLoginResponse.refreshToken)
                paperPrefs.setRole(loginResponse.dataLoginResponse.roleId)

                Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                navigateTo(navController, Screen.Home.route)
            }
        }
        coroutineScope.launch {
            viewModel.showLoading.collect {
                showLoading = it
            }
        }
        coroutineScope.launch {
            viewModel.errorResponse.collect { errorResponse ->
                Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MyStyle.colors.bgWhite)
            .padding(horizontal = 16.sdp)
    ) {
        Text(
            text = "Remangok Babel",
            style = MyStyle.typography.baseBold,
            fontSize = 28.ssp,
            color = MyStyle.colors.textPrimary,
            modifier = Modifier
                .padding(top = 24.sdp)
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = "Hai, Selamat Datang di Remangok Babel 🦀",
            style = MyStyle.typography.xsSemibold,
            color = MyStyle.colors.textBlack,
            modifier = Modifier.padding(top = 16.sdp)
        )
        Text(
            text = "Silahkan Masuk",
            style = MyStyle.typography.xssMedium,
            color = MyStyle.colors.textGrey,
        )
        InputLayout(
            title = "Email",
            value = email,
            hint = "Silahkan masukkan email anda",
            modifier = Modifier
                .padding(top = 16.sdp)
        ) {
            email = it
        }
        InputLayout(
            title = "Kata Sandi",
            value = password,
            hint = "Silahkan masukkan kata sandi anda",
            modifier = Modifier
                .padding(top = 16.sdp),
            isPassword = true
        ) {
            password = it
        }
        Spacer(modifier = Modifier.weight(1f))
        ButtonCustom(
            text = "Masuk",
            enabled = email.isNotEmpty() && password.isNotEmpty(),
            modifier = Modifier
        ) {
            viewModel.login(LoginRequest(email, password))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.sdp, bottom = 24.sdp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Belum punya akun?")
            Text(
                text = "Daftar disini",
                color = MyStyle.colors.textHijau,
                modifier = Modifier
                    .padding(start = 4.sdp)
                    .clickable {
                        navigateTo(navController, Screen.Register.route)
                    }
            )
        }
    }
}