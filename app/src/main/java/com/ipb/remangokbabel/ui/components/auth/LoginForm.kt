package com.ipb.remangokbabel.ui.components.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import com.ipb.remangokbabel.ui.viewmodel.AuthViewModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    onSuccessLogin: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (BuildConfig.DEBUG) {
            email = "sumbul2"
            password = "password"
        }
        coroutineScope.launch {
            viewModel.loginResponse.collect { loginResponse ->
                println("Masuk LaunchedEffect loginResponse")
                paperPrefs.setAccessToken(loginResponse.dataLoginResponse.accessToken)
                paperPrefs.setRefreshToken(loginResponse.dataLoginResponse.refreshToken)
                Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                onSuccessLogin()
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

    Column(modifier) {
        InputLayout(
            title = "Email",
            value = email,
            hint = "Silahkan masukkan email anda",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            email = it
        }
        InputLayout(
            title = "Kata Sandi",
            value = password,
            hint = "Silahkan masukkan kata sandi anda",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp),
            isPassword = true
        ) {
            password = it
        }

        ButtonCustom(
            text = "Masuk",
            enabled = email.isNotEmpty() && password.isNotEmpty(),
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 24.sdp)
        ) {
            viewModel.login(LoginRequest(email, password))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.sdp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Belum punya akun?")
            Text(
                text = "Daftar disini",
                modifier = Modifier
                    .padding(start = 4.sdp),
                color = MyStyle.colors.textHijau,
            )
        }

    }
    if (showLoading) {
        LoadingDialog()
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun LoginFormPreview() {
    val viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
    RemangokBabelTheme {
        Column {
            LoginForm(viewModel = viewModel) {}
        }
    }
}


