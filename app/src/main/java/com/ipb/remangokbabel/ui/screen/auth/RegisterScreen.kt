package com.ipb.remangokbabel.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.ExposedDropdownMenuBox
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.AuthViewModel
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var showLoading by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Pembeli") }


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
                navigateToBack(navController)
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

    if (showLoading) {
        LoadingDialog()
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MyStyle.colors.bgWhite)
            ) {
                ButtonCustom(
                    text = "Daftar",
                    enabled = email.isNotEmpty() && password.isNotEmpty(),
                    modifier = Modifier
                        .shadow(
                            elevation = 10.sdp, // Adjust the elevation as needed
                            shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                            clip = false // Don't clip the content to the shape
                        )
                        .background(color = MyStyle.colors.bgWhite)
                        .padding(horizontal = 16.sdp)
                        .padding(top = 16.sdp)
                ) {
                    if (password != confirmPassword) {
                        Toast.makeText(context, "Kata sandi tidak sama", Toast.LENGTH_SHORT).show()
                        return@ButtonCustom
                    }
                    viewModel.register(
                        RegisterRequest(
                            username = username,
                            email = email,
                            password = password,
                            fullname = fullname,
                            nomor_telepon = phone,
                            role = role.lowercase()
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MyStyle.colors.bgWhite)
                        .padding(top = 16.sdp, bottom = 24.sdp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Sudah punya akun?", style = MyStyle.typography.xsMedium)
                    Text(
                        text = "Masuk",
                        style = MyStyle.typography.xsMedium,
                        color = MyStyle.colors.textHijau,
                        modifier = Modifier
                            .padding(start = 4.sdp)
                            .clickable {
                                navigateToBack(navController)
                            }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(bottom = innerPadding.calculateBottomPadding())
                .fillMaxSize()
                .background(color = MyStyle.colors.bgWhite)
                .padding(horizontal = 16.sdp)
                .verticalScroll(rememberScrollState())
                .imePadding()
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
                text = "Silahkan Daftar",
                style = MyStyle.typography.xsMedium,
                color = MyStyle.colors.textGrey,
            )
            ExposedDropdownMenuBox(
                listOf("Pembeli", "Penjual"),
                selectedText = role,
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) {
                role = it
            }
            InputLayout(
                title = "Nama Lengkap",
                value = fullname,
                hint = "Silahkan masukkan nama anda",
                modifier = Modifier
                    .padding(top = 8.sdp)
            ) {
                fullname = it
            }
            InputLayout(
                title = "Nama Pengguna",
                value = username,
                hint = "Silahkan masukkan nama pengguna anda",
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) {
                username = it
            }
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
                title = "Nomor HP",
                value = phone,
                hint = "Silahkan masukkan nomor hp anda",
                modifier = Modifier
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
                    .padding(vertical = 16.sdp)
            ) {
                confirmPassword = it
            }
        }
    }
}