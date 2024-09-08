package com.ipb.remangokbabel.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.profile.ProfileAddressCard
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ListProfileScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val coroutineScope = rememberCoroutineScope()

    var address by remember {
        mutableStateOf(
            listOf(
                ProfilesItem(
                    id = "1",
                    namaDepan = "Danil",
                    namaBelakang = "Ardi",
                    nomorTelepon = "081122334455",
                    alamat = "rumahh",
                    namaKelurahan = "Kelurahan",
                    namaKecamatan = "Kecamatan",
                    namaKotaKabupaten = "Kota Kabupaten",
                    namaProvinsi = "Provinsi",
                    kodePos = "Kode Pos",
                    createdAt = "Created At",
                    updatedAt = "Updated At",
                ),
                ProfilesItem(
                    id = "2",
                    namaDepan = "Danil",
                    namaBelakang = "Ardi",
                    nomorTelepon = "081122334455",
                    alamat = "rumahh",
                    namaKelurahan = "Kelurahan",
                    namaKecamatan = "Kecamatan",
                    namaKotaKabupaten = "Kota Kabupaten",
                    namaProvinsi = "Provinsi",
                    kodePos = "Kode Pos",
                    createdAt = "Created At",
                    updatedAt = "Updated At",
                ),
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.getProfile()

        coroutineScope.launch {
            viewModel.getProfileResponse.collect {
                address = it.data.profiles
            }
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                title = "Daftar Alamat",
                onClickBackButton = { navigateToBack(navController) })
        },
        bottomBar = {
            ButtonCustom(
                text = "Tambah Alamat",
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.sdp, // Adjust the elevation as needed
                        shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                        clip = false // Don't clip the content to the shape
                    )
                    .background(color = MyStyle.colors.bgWhite)
                    .padding(16.sdp)
            ) {
                navigateTo(navController, Screen.AddProfile.route)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(address, key = { it.id }) { item ->
                    ProfileAddressCard(item = item, onClickDetail = {
                    })
                }
            }
        }
    }
}