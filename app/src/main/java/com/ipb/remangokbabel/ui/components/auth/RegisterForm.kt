package com.ipb.remangokbabel.ui.components.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.ExposedDropdownMenuBox
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import com.ipb.remangokbabel.ui.viewmodel.AuthViewModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch


@Composable
fun RegisterForm(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    onClickLogin: () -> Unit,
    onSuccessRegis: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Pembeli") }

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (BuildConfig.DEBUG) {
            username = "medomeckz"
            email = "medomeckz@gmail.com"
            password = "password"
            confirmPassword = "password"
            fullname = "Medo Meckz"
            phone = "081234567890"
        }
        coroutineScope.launch {
            viewModel.registerResponse.collect {
                Toast.makeText(context, "Register Berhasil", Toast.LENGTH_SHORT).show()
                onSuccessRegis()
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
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.sdp)
    ) {
        ExposedDropdownMenuBox(
            listOf("Pembeli", "Penjual"),
            selectedText = role,
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            role = it
        }
        InputLayout(
            title = "Nama Lengkap",
            value = fullname,
            hint = "Silahkan masukkan nama anda",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            fullname = it
        }
        InputLayout(
            title = "Nama Pengguna",
            value = username,
            hint = "Silahkan masukkan nama pengguna anda",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            username = it
        }
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
            title = "Nomor HP",
            value = phone,
            hint = "Silahkan masukkan nomor hp anda",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            phone = it
        }
        InputLayout(
            title = "Kata Sandi",
            value = password,
            hint = "Silahkan masukkan kata sandi anda",
            isPassword = true,
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            password = it
        }
        InputLayout(
            title = "Konfirmasi Kata Sandi",
            value = confirmPassword,
            hint = "Silahkan masukkan ulang sandi anda",
            isPassword = true,
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            confirmPassword = it
        }

        ButtonCustom(
            text = "Daftar",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 24.sdp)
        ) {
            viewModel.register(RegisterRequest(
                username = username,
                email = email,
                password = password,
                fullname = fullname,
                nomor_telepon = phone,
                role = role
            ))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.sdp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Sudah punya akun?")
            Text(
                text = "Masuk",
                modifier = Modifier
                    .clickable {
                        onClickLogin()
                    }
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
private fun RegisterFormPreview() {
    val viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
    RemangokBabelTheme {
        RegisterForm(viewModel = viewModel, onSuccessRegis = {}, onClickLogin = {})
    }
}