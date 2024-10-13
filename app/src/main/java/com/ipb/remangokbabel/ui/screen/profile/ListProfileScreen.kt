package com.ipb.remangokbabel.ui.screen.profile

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.component.ScreenType
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.EmptyScreen
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.profile.ProfileAddressCard
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ListProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = remember { PaperPrefs(context) }
    var showLoading by remember { mutableStateOf(false) }

    var address by remember { mutableStateOf(emptyList<ProfilesItem>()) }

    LaunchedEffect(Unit) {
        viewModel.getProfile()

        coroutineScope.launch {
            viewModel.getProfileResponse.collect {
                address = it.data.profiles
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
                if (errorResponse.message == "token anda tidak valid") {
                    paperPrefs.deleteAllData()
                    navigateToAndMakeTop(navController, Screen.Login.route)
                }
            }
        }
    }

    if (showLoading) {
        return LoadingDialog()
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
            if (address.isEmpty()) {
                EmptyScreen(type = ScreenType.Empty)
            }
            LazyColumn {
                items(address, key = { it.id }) { item ->
                    ProfileAddressCard(item = item, onClickDetail = {
                        val data = Uri.encode(Gson().toJson(item))
                        navigateTo(navController, Screen.EditProfile.createRoute(data))
                    },
                        modifier = Modifier.background(MyStyle.colors.bgWhite))
                    HorizontalDivider(
                        thickness = 1.sdp,
                        color = MyStyle.colors.bgSecondary
                    )
                }
            }
        }
    }
}