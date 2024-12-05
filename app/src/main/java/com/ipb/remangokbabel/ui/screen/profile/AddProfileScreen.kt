package com.ipb.remangokbabel.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.model.request.AddProfileRequest
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.capitalizeEachWord
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AddProfileScreen(
    modifier: Modifier = Modifier,
    data: ProfilesItem? = null,
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)
    var showLoading by remember { mutableStateOf(false) }

    val returnedData =
        navController.currentBackStackEntry?.savedStateHandle?.get<List<String>>("data_key")
            ?: listOf("", "", "", "")

    var isEdit by remember { mutableStateOf(false) }

    var namaDepan by rememberSaveable { mutableStateOf("") }
    var namaBelakang by rememberSaveable { mutableStateOf("") }
    var nomorTelepon by rememberSaveable { mutableStateOf("") }
    var alamat by rememberSaveable { mutableStateOf("") }
    var namaKelurahan by rememberSaveable { mutableStateOf("") }
    var namaKecamatan by rememberSaveable { mutableStateOf("") }
    var namaKotaKabupaten by rememberSaveable { mutableStateOf("") }
    var namaProvinsi by rememberSaveable { mutableStateOf("") }
    var kodePos by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (BuildConfig.DEBUG) {
//            namaDepan = "Danil"
//            namaBelakang = "Ardi"
//            nomorTelepon = "081122334455"
//            alamat = "rumahh"
//            namaKelurahan = "Kelurahan"
//            namaKecamatan = "Kecamatan"
//            namaKotaKabupaten = "Kota Kabupaten"
//            namaProvinsi = "Provinsi"
//            kodePos = "21313"
        }
//        if (data != null) {
//            isEdit = true
//            namaDepan = data.namaDepan
//            namaBelakang = data.namaBelakang
//            nomorTelepon = data.nomorTelepon
//            alamat = data.alamat
//            namaKelurahan = data.namaKelurahan
//            namaKecamatan = data.namaKecamatan
//            namaKotaKabupaten = data.namaKotaKabupaten
//            namaProvinsi = data.namaProvinsi
//            kodePos = data.kodePos
//        }

//        coroutineScope.launch {
//            viewModel.addProfileResponse.collect {
//                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                navigateToBack(navController)
//            }
//        }
//
//        coroutineScope.launch {
//            viewModel.updateProfileResponse.collect {
//                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                navigateToBack(navController)
//            }
//        }
//
//        coroutineScope.launch {
//            viewModel.deleteProfileResponse.collect {
//                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                navigateToBack(navController)
//            }
//        }
//
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
    LaunchedEffect(returnedData) {
        if (returnedData[3].isNotEmpty()) {
            namaKelurahan = returnedData[3].capitalizeEachWord()
            namaKecamatan = returnedData[2].capitalizeEachWord()
            namaKotaKabupaten = returnedData[1].capitalizeEachWord()
            namaProvinsi = returnedData[0].capitalizeEachWord()
        }
    }

    if (showLoading) {
        LoadingDialog()
    }

    Scaffold(
        topBar = {
            BackTopBar(title = if (isEdit) "Edit Alamat" else "Tambah Alamat") {
                navigateToBack(navController)
            }
        },
        bottomBar = {
            Row {
                if (isEdit) {
                    ButtonCustom(
                        text = "Hapus",
                        type = ButtonType.Danger,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.sdp),
                        onClick = {
                        },
                    )
                }
                ButtonCustom(
                    text = "Simpan",
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.sdp),
                    enabled = namaDepan.isNotEmpty() &&
                            namaBelakang.isNotEmpty() &&
                            nomorTelepon.isNotEmpty() &&
                            alamat.isNotEmpty() &&
                            namaKelurahan.isNotEmpty() &&
                            namaKecamatan.isNotEmpty() &&
                            namaKotaKabupaten.isNotEmpty() &&
                            namaProvinsi.isNotEmpty() &&
                            kodePos.isNotEmpty(),
                    onClick = {
                        val request = AddProfileRequest(
                            namaDepan = namaDepan,
                            namaBelakang = namaBelakang,
                            nomorTelepon = nomorTelepon,
                            alamat = alamat,
                            namaKelurahan = namaKelurahan,
                            namaKecamatan = namaKecamatan,
                            namaKotaKabupaten = namaKotaKabupaten,
                            namaProvinsi = namaProvinsi,
                            kodePos = kodePos
                        )
                        println(request)
                        if (isEdit) {
//                        viewModel.updateProfile(data!!.id, request)
                        } else {
//                        viewModel.addProfile(request)
                        }
                    },
                )
            }
        }) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .fillMaxSize()
                .background(MyStyle.colors.bgSecondary)
                .imePadding()
        ) {
            Text(
                text = "Data Diri", style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 16.sdp)
                    .padding(top = 8.sdp)
            )
            InputLayout(
                value = namaDepan,
                hint = "Nama Depan",
                border = false,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 16.sdp)
                    .padding(bottom = 2.sdp)
            ) {
                namaDepan = it
            }
            InputLayout(
                value = namaBelakang,
                hint = "Nama Belakang",
                border = false,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .padding(top = 1.sdp)
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 16.sdp)
                    .padding(vertical = 2.sdp)
            ) {
                namaBelakang = it
            }
            InputLayout(
                value = nomorTelepon,
                hint = "Nomor Telepon",
                border = false,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .padding(top = 1.sdp)
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 16.sdp)
                    .padding(vertical = 2.sdp)
            ) {
                nomorTelepon = it
            }
            Text(
                text = "Alamat", style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 4.sdp)
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 16.sdp)
                    .padding(top = 8.sdp)
            )
            Row(
                Modifier
                    .background(MyStyle.colors.bgWhite)
                    .clickable {
                    }
                    .padding(horizontal = 16.sdp, vertical = 8.sdp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (namaKelurahan.isNotEmpty()) "${namaKelurahan}\n${namaKecamatan}\n$namaKotaKabupaten\n${namaProvinsi}" else "Pilih Alamat",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = MyStyle.colors.primaryMain,
                    modifier = Modifier.size(16.sdp)
                )
            }
            InputLayout(
                value = alamat,
                hint = "Detail Alamat (ex: Rumah kuning, RT/RW, dll)",
                border = false,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 16.sdp)
                    .padding(bottom = 2.sdp)
            ) {
                alamat = it
            }
            InputLayout(
                value = kodePos,
                hint = "Kode Pos",
                border = false,
                maxLength = 5,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 16.sdp)
                    .padding(bottom = 2.sdp)
            ) {
                kodePos = it
            }
        }
    }
}