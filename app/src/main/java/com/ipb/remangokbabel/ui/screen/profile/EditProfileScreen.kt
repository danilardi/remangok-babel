package com.ipb.remangokbabel.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.ProfileRequest
import com.ipb.remangokbabel.model.response.GetKecamatanResponseItem
import com.ipb.remangokbabel.model.response.GetKelurahanResponseItem
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.ExposedDropdownMenuBox
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.capitalizeEachWord
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    profileData: ProfilesItem? = null
) {
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var nik by remember { mutableStateOf("") }
    var kecamatan by remember { mutableStateOf("") }
    var kelurahan by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var kodePos by remember { mutableStateOf("") }

    var listKecamatan by remember { mutableStateOf(emptyList<GetKecamatanResponseItem>()) }
    var listKelurahan by remember { mutableStateOf(emptyList<GetKelurahanResponseItem>()) }

    LaunchedEffect(Unit) {
        nik = profileData?.nik ?: ""
        kecamatan = profileData?.kecamatan ?: ""
        kelurahan = profileData?.kelurahan ?: ""
        alamat = profileData?.alamat ?: ""
        kodePos = profileData?.kodePos ?: ""

        viewModel.getKecamatan("1904")
    }

    viewModel.getKecamatanState.collectAsState().value.let {
        listKecamatan = it
    }

    viewModel.getKelurahanState.collectAsState().value.let {
        listKelurahan = it
    }

    viewModel.updateProfileState.collectAsState().value?.let {
        viewModel.clearUpdateProfileState()
        viewModel.getProfile()
    }

    viewModel.getProfileState.collectAsState().value?.let {
        Toast.makeText(context, "Berhasil memperhabarui profil", Toast.LENGTH_SHORT).show()
        LaunchedEffect(Unit) {
            paperPrefs.setProfile(it.data.profiles)
            viewModel.clearProfileState()
            navigateToBack(navController)
        }
    }

    viewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    viewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    Scaffold(
        topBar = {
            BackTopBar(title = "Profil") {
                navigateToBack(navController)
            }
        },
        bottomBar = {
            ButtonCustom(
                text = "Simpan",
                enabled = nik.isNotEmpty() &&
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
                    .padding(bottom = 24.sdp)
            ) {
                val profileRequest = ProfileRequest(
                    nik = nik,
                    kecamatan = kecamatan,
                    kelurahan = kelurahan,
                    alamat = alamat,
                    kodePos = kodePos
                )
                viewModel.updateProfile(profileRequest)
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(horizontal = 16.sdp)
            .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "",
                modifier = Modifier
                    .size(63.sdp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.sdp),
            )
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
            InputLayout(
                title = "Kabupaten/Kota",
                value = "Kabupaten Bangka Tengah",
                isEnable = false,
                modifier = Modifier
                    .padding(top = 16.sdp)
            )
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
                    viewModel.getKelurahan(id)
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
        }
    }
}