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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.AddProfileRequest
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun AddProfileScreen(
    data: ProfilesItem = ProfilesItem(
        id = "1",
        namaDepan = "Danil",
        namaBelakang = "Ardi",
        nomorTelepon = "081122334455",
        alamat = "rumahh",
        namaKelurahan = "Kelurahan",
        namaKecamatan = "Kecamatan",
        namaKotaKabupaten = "Kota Kabupaten",
        namaProvinsi = "Provinsi",
        kodePos = "213123",
        createdAt = "Created At",
        updatedAt = "Updated At",
    ),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val returnedData = navController.currentBackStackEntry?.savedStateHandle?.get<List<String>>("data_key") ?: listOf("", "", "", "")

    var isEdit by remember { mutableStateOf(false) }

    var namaDepan by remember { mutableStateOf("") }
    var namaBelakang by remember { mutableStateOf("") }
    var nomorTelepon by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var namaKelurahan by remember { mutableStateOf("") }
    var namaKecamatan by remember { mutableStateOf("") }
    var namaKotaKabupaten by remember { mutableStateOf("") }
    var namaProvinsi by remember { mutableStateOf("") }
    var kodePos by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (BuildConfig.DEBUG) {
            namaDepan = data.namaDepan
            namaBelakang = data.namaBelakang
            nomorTelepon = data.nomorTelepon
            alamat = data.alamat
//            namaKelurahan = data.namaKelurahan
//            namaKecamatan = data.namaKecamatan
//            namaKotaKabupaten = data.namaKotaKabupaten
//            namaProvinsi = data.namaProvinsi
            kodePos = data.kodePos
            isEdit = true
        }
    }
    LaunchedEffect(returnedData) {
        namaProvinsi = returnedData[0]
        namaKotaKabupaten = returnedData[1]
        namaKecamatan = returnedData[2]
        namaKelurahan = returnedData[3]
    }

    Scaffold(topBar = {
        BackTopBar(title = if (isEdit) "Edit Alamat" else "Tambah Alamat") {
            navigateToBack(navController)
        }
    }, bottomBar = {
        ButtonCustom(
            text = "Simpan",
            modifier = Modifier.padding(16.sdp),
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
//                if (isEdit) {
//                    viewModel.updateProfile(data.id, request)
//                } else {
//                    viewModel.addProfile(request)
//                }
            },
        )
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
                text = "Data Diri", style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(top = 4.sdp)
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 16.sdp)
                    .padding(top = 8.sdp)
            )
            InputLayout(
                value = namaDepan,
                hint = "Nama Depan",
                border = false,
                textStyle = MaterialTheme.typography.bodySmall.copy(
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
                textStyle = MaterialTheme.typography.bodySmall.copy(
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
                textStyle = MaterialTheme.typography.bodySmall.copy(
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
                text = "Alamat", style = MaterialTheme.typography.bodySmall,
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
                        navigateTo(navController, Screen.SelectAddress.route)
                    }
                    .padding(horizontal = 16.sdp, vertical = 8.sdp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (returnedData[3].isNotEmpty()) "${returnedData[0]}\n${returnedData[1]}\n${returnedData[2]}\n${returnedData[3]}" else "Pilih Alamat",
                    style = MaterialTheme.typography.bodySmall,
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
                textStyle = MaterialTheme.typography.bodySmall.copy(
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
                textStyle = MaterialTheme.typography.bodySmall.copy(
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