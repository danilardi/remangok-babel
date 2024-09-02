package com.ipb.remangokbabel.ui.components.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.ErrorResponse
import com.ipb.remangokbabel.model.LoginRequest
import com.ipb.remangokbabel.ui.ViewModelFactory
import com.ipb.remangokbabel.ui.common.UiState
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.screen.auth.AuthViewModel
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import com.spr.jetpack_loading.components.indicators.BallSpinFadeLoaderIndicator
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    onSuccessLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showLoading by remember { mutableStateOf(false) }

    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    LaunchedEffect(Unit) {
        if (BuildConfig.DEBUG) {
            email = "sumbul1"
            password = "password"
        }
    }
    LaunchedEffect(loginState) {
        when (loginState) {
            is UiState.Idle -> {
                // No action needed for Idle state
            }

            is UiState.Loading -> {
                showLoading = true
            }

            is UiState.Success -> {
                showLoading = false
                Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                paperPrefs.setAccessToken((loginState as UiState.Success).data.dataLoginResponse.accessToken)
                paperPrefs.setRefreshToken((loginState as UiState.Success).data.dataLoginResponse.refreshToken)
                onSuccessLogin()
            }

            is UiState.Error<*> -> {
                showLoading = false
                val errorResponse = (loginState as UiState.Error<*>).errorData
                if (errorResponse is ErrorResponse) {
                    Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
                }
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
//    LoadingDialog(showLoading)
    if (showLoading) {
        Dialog(
            onDismissRequest = {},
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment= Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(MyStyle.colors.backgroundSecondary, shape = RoundedCornerShape(8.dp))
            ) {
                BallSpinFadeLoaderIndicator(
                    color = MyStyle.colors.backgroundPrimary
                )
            }
        }
    }
}

@Composable
fun LoadingDialog(
    showLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    if (showLoading) return
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}


@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun LoginFormPreview() {
    val viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
    RemangokBabelTheme {
        LoginForm(viewModel = viewModel) {

        }
    }
}
