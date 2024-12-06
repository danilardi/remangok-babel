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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import com.ipb.remangokbabel.model.request.ProfileRequest
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.model.response.GetKabupatenKotaResponseItem
import com.ipb.remangokbabel.model.response.GetKecamatanResponseItem
import com.ipb.remangokbabel.model.response.GetKelurahanResponseItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.ExposedDropdownMenuBox
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.AuthViewModel
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.capitalizeEachWord
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun RegisterScreen(
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

    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nomorTelepon by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    var kotaKabupaten by remember { mutableStateOf("") }
    var kecamatan by remember { mutableStateOf("") }
    var kelurahan by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var kodePos by remember { mutableStateOf("") }

    var listKotaKabupaten by remember {
        mutableStateOf(emptyList<GetKabupatenKotaResponseItem>())
    }
    var listKecamatan by remember { mutableStateOf(emptyList<GetKecamatanResponseItem>()) }
    var listKelurahan by remember { mutableStateOf(emptyList<GetKelurahanResponseItem>()) }

    val registerState by authViewModel.registerResponse.collectAsState()

    LaunchedEffect(Unit) {
        if (BuildConfig.DEBUG) {
            email = "medomeckz@gmail.com"
            password = "password"
            confirmPassword = "password"
            fullname = "Medo Meckz"
            nomorTelepon = "6281234567890"
            nik = "1231231231231231"
            alamat = "Jl. Jend. Sudirman No. 1"
            kodePos = "33111"
        }
        profileViewModel.getKotaKabupaten()
    }

    LaunchedEffect(registerState) {
        if (registerState != null) {
            Toast.makeText(context, "Register Berhasil", Toast.LENGTH_SHORT).show()
            paperPrefs.setEmailSaved(email)
            navigateToBack(navController)
        }
    }

    profileViewModel.getKotaKabupatenState.collectAsState().value.let { 
        listKotaKabupaten = it
    }

    profileViewModel.getKecamatanState.collectAsState().value.let {
        listKecamatan = it
    }

    profileViewModel.getKelurahanState.collectAsState().value.let {
        listKelurahan = it
    }

    profileViewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            profileViewModel.clearError()
        }
    }

    authViewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            authViewModel.clearError()
        }
    }

    profileViewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    authViewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    Scaffold(
        topBar = {
            BackTopBar(title = "Daftar", onClickBackButton = {
                navigateToBack(navController)
            })
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MyStyle.colors.bgWhite)
            ) {
                ButtonCustom(
                    text = "Daftar",
                    enabled = email.isNotEmpty() &&
                            password.isNotEmpty() &&
                            confirmPassword.isNotEmpty() &&
                            fullname.isNotEmpty() &&
                            nomorTelepon.isNotEmpty() &&
                            nik.isNotEmpty() &&
                            kotaKabupaten.isNotEmpty() &&
                            kecamatan.isNotEmpty() &&
                            kelurahan.isNotEmpty() &&
                            alamat.isNotEmpty() &&
                            kodePos.isNotEmpty(),
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

                    authViewModel.register(
                        RegisterRequest(
                            email = email,
                            password = password,
                            fullname = fullname,
                            nomorTelepon = if (nomorTelepon.startsWith("0")) {
                                "62${nomorTelepon.substring(1)}"
                            } else {
                                nomorTelepon
                            },
                            profile = ProfileRequest(
                                nik = nik,
                                kecamatan = kecamatan,
                                kotaKabupaten = kotaKabupaten,
                                kelurahan = kelurahan,
                                alamat = alamat,
                                kodePos = kodePos
                            )
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
                    Text(
                        text = "Sudah punya akun?",
                        fontWeight = FontWeight.W400,
                        color = MyStyle.colors.textBlack,
                    )
                    Text(
                        text = "Masuk",
                        color = MyStyle.colors.primaryMain,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier
                            .padding(start = 6.sdp)
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
                text = "Buat Akun Remangkok Babel  🦀",
                style = MyStyle.typography.baseBold,
                color = MyStyle.colors.textBlack,
                modifier = Modifier.padding(top = 16.sdp)
            )
            InputLayout(
                title = "Nama Lengkap",
                value = fullname,
                hint = "Silahkan masukkan nama anda",
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) {
                fullname = it
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
                value = nomorTelepon,
                hint = "ex: 08xxxxx atau 62xxxxx",
                maxLength = 13,
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) {
                nomorTelepon = it
            }
            InputLayout(
                title = "NIK",
                value = nik,
                hint = "contoh: 1232xxxxx (16 digit)",
                maxLength = 16,
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) {
                nik = it
            }
            ExposedDropdownMenuBox(
                items = listKotaKabupaten.map { it.name.capitalizeEachWord() },
                title = "Kota/Kabupaten",
                hint = "Silahkan pilih Kota/Kabupaten",
                selectedText = kotaKabupaten,
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) { value ->
                kotaKabupaten = value
                val id = listKotaKabupaten.find { it.name.capitalizeEachWord() == value }?.id
                if (id != null) {
                    profileViewModel.getKecamatan(id)
                }
            }
            ExposedDropdownMenuBox(
                items = listKecamatan.map { it.name.capitalizeEachWord() },
                title = "Kecamatan",
                hint = "Silahkan pilih kecamatan",
                selectedText = kecamatan,
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) { value ->
                kecamatan = value
                val id = listKecamatan.find { it.name.capitalizeEachWord() == value }?.id
                if (id != null) {
                    profileViewModel.getKelurahan(id)
                }
            }
            ExposedDropdownMenuBox(
                items = listKelurahan.map { it.name.capitalizeEachWord() },
                title = "Kelurahan",
                hint = "Silahkan pilih kelurahan",
                isEnable = kecamatan.isNotEmpty(),
                selectedText = kelurahan,
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) {
                kelurahan = it
            }
            InputLayout(
                title = "Alamat",
                value = alamat,
                hint = "Contoh: Jl. Jend. Sudirman No. 1",
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) {
                alamat = it
            }
            InputLayout(
                title = "Kode Pos",
                value = kodePos,
                hint = "Silahkan masukkan kode pos anda",
                maxLength = 5,
                modifier = Modifier
                    .padding(top = 16.sdp)
            ) {
                kodePos = it
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