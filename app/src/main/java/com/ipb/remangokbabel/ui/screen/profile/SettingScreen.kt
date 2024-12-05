package com.ipb.remangokbabel.ui.screen.profile

import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract.Colors
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    val profileData = paperPrefs.getProfile()

    viewModel.logoutState.collectAsState().value?.let {
        Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
        paperPrefs.deleteAllData()
        viewModel.clearLogoutState()
        navigateToAndMakeTop(navController, Screen.Login.route)
    }

    viewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    viewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            if (it.message == "token anda tidak valid") {
                paperPrefs.deleteAllData()
                navigateToAndMakeTop(navController, Screen.Login.route)
            }
        }
        viewModel.clearError()
    }

    Scaffold(
        topBar = {
            BackTopBar(title = "Profile", onClickBackButton = {
                navigateToBack(navController)
            })
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MyStyle.colors.bgWhite)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.sdp)
                    .padding(top = 8.sdp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "icon profile",
                    modifier = Modifier
                        .size(63.sdp)
                        .align(Alignment.CenterHorizontally),
                )
                ProfileItem(label = "NIK", value = profileData?.nik ?: "")
                ProfileItem(label = "Nama", value = profileData?.dataDiri?.fullname ?: "")
                ProfileItem(label = "Email", value = profileData?.dataDiri?.email ?: "")
                ProfileItem(
                    label = "Nomor Telepon",
                    value = profileData?.dataDiri?.nomorTelepon ?: ""
                )
                ProfileItem(label = "Provinsi", value = "Kepulauan Bangka Belitung")
                ProfileItem(label = "Kabupaten/Kota", value = profileData?.kotaKabupaten ?: "")
                ProfileItem(label = "Kecamatan", value = profileData?.kecamatan ?: "")
                ProfileItem(label = "Kelurahan", value = profileData?.kelurahan ?: "")
                ProfileItem(label = "Alamat", value = profileData?.alamat ?: "")
                ProfileItem(label = "Kode Pos", value = profileData?.kodePos ?: "")
            }
            HorizontalDivider()
            MenuItem(
                title = "Perbarui Profil",
                onClick = {
                    val data = Uri.encode(Gson().toJson(profileData))
                    navigateToAndMakeTop(navController, Screen.EditProfile.createRoute(data))
                }
            )
            MenuItem(
                title = "Panduan Aplikasi",
                onClick = {
                    val url =
                        "https://drive.google.com/drive/folders/1-yfATreiuyU7REd_dQRorx0heRbzO1-q"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .padding(top = 2.sdp)
            )
            MenuItem(
                title = "Keluar",
                isLast = true,
                onClick = {
                    viewModel.logout()
                },
                modifier = Modifier
                    .padding(top = 2.sdp)
            )
        }
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    title: String,
    isLast: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(MyStyle.colors.bgWhite)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Text(
            text = title,
            style = MyStyle.typography.baseBold,
            fontSize = 12.sp,
            color = MyStyle.colors.textBlack,
            modifier = Modifier
                .padding(horizontal = 16.sdp, vertical = 16.sdp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.sdp)
        )
    }
    if(!isLast) HorizontalDivider()
}

@Composable
fun ProfileItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(bottom = 8.sdp)) {
        Text(
            text = label,
            style = MyStyle.typography.baseBold,
            color = MyStyle.colors.neutral100,
            fontSize = 12.sp,
            modifier = modifier.padding(top = 16.dp)
        )
        Text(
            text = value,
            style = MyStyle.typography.baseMedium,
            fontSize = 12.sp,
            color = MyStyle.colors.neutral600,
            modifier = modifier.padding(top = 6.dp)

        )
    }
}