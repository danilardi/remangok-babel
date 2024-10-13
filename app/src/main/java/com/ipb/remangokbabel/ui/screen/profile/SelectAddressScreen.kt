package com.ipb.remangokbabel.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.model.response.GetKabupatenKotaResponseItem
import com.ipb.remangokbabel.model.response.GetKecamatanResponseItem
import com.ipb.remangokbabel.model.response.GetKelurahanResponseItem
import com.ipb.remangokbabel.model.response.GetProvinsiResponseItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.navigateToBack
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun SelectAddressScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var showLoading by remember { mutableStateOf(false) }

    var listProvinsi by remember { mutableStateOf(emptyList<GetProvinsiResponseItem>()) }
    var listKabupatenKota by remember { mutableStateOf(emptyList<GetKabupatenKotaResponseItem>()) }
    var listKecamatan by remember { mutableStateOf(emptyList<GetKecamatanResponseItem>()) }
    var listKelurahan by remember { mutableStateOf(emptyList<GetKelurahanResponseItem>()) }

    var selectedProvinsi by remember { mutableStateOf("KEPULAUAN BANGKA BELITUNG") }
    var selectedKabuptatenKota by remember { mutableStateOf("") }
    var selectedKecamatan by remember { mutableStateOf("") }
    var selectedKelurahan by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getKabupatenKota("19")

        coroutineScope.launch {
            viewModel.getProvinsiResponse.collect {
                listProvinsi = it
            }
        }
        coroutineScope.launch {
            viewModel.getKabupatenKotaResponse.collect {
                listKabupatenKota = it
            }
        }
        coroutineScope.launch {
            viewModel.getKecamatanResponse.collect {
                listKecamatan = it
            }
        }
        coroutineScope.launch {
            viewModel.getKelurahanResponse.collect {
                listKelurahan = it
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
        topBar = {
            BackTopBar(title = "Pilih Alamat", onClickBackButton = { navigateToBack(navController) })
        },
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.sdp, // Adjust the elevation as needed
                        shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                        clip = false // Don't clip the content to the shape
                    )
                    .background(color = MyStyle.colors.bgWhite)
            ){
                ButtonCustom(
                    text = "Batal",
                    type = ButtonType.Outline,
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.sdp)
                ) {
                    navigateToBack(navController)
                }
                ButtonCustom(
                    text = "Pilih Alamat",
                    type = ButtonType.Primary,
                    enabled = selectedKelurahan.isNotEmpty(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.sdp)
                ) {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "data_key",
                        listOf(
                            selectedProvinsi,
                            selectedKabuptatenKota,
                            selectedKecamatan,
                            selectedKelurahan
                        )
                    )
                    navigateToBack(navController)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = "Provinsi",
                style = MaterialTheme.typography.titleMedium,
                color = MyStyle.colors.textPrimary,
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(top = 8.sdp)
            )
            if (selectedProvinsi.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
//                        .clickable {
//                            selectedProvinsi = ""
//                            selectedKabuptatenKota = ""
//                            selectedKecamatan = ""
//                            selectedKelurahan = ""
//                        }
                        .padding(horizontal = 16.sdp, vertical = 8.sdp)
                ) {
                    Text(
                        text = selectedProvinsi,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MyStyle.colors.primaryMain,
                        modifier = Modifier.size(16.sdp)
                    )
                }

                Text(
                    text = "Kabupaten/Kota",
                    style = MaterialTheme.typography.titleMedium,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier
                        .padding(horizontal = 16.sdp)
                        .padding(top = 8.sdp)
                )
            }
            if (selectedKabuptatenKota.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedKabuptatenKota = ""
                            selectedKecamatan = ""
                            selectedKelurahan = ""
                        }
                        .padding(horizontal = 16.sdp, vertical = 8.sdp)
                ) {
                    Text(
                        text = selectedKabuptatenKota,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MyStyle.colors.primaryMain,
                        modifier = Modifier.size(16.sdp)
                    )
                }
                Text(
                    text = "Kecamatan",
                    style = MaterialTheme.typography.titleMedium,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier
                        .padding(horizontal = 16.sdp)
                        .padding(top = 8.sdp)
                )
            }
            if (selectedKecamatan.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedKecamatan = ""
                            selectedKelurahan = ""
                        }
                        .padding(horizontal = 16.sdp, vertical = 8.sdp)
                ) {
                    Text(
                        text = selectedKecamatan,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MyStyle.colors.primaryMain,
                        modifier = Modifier.size(16.sdp)
                    )
                }
                Text(
                    text = "Kelurahan",
                    style = MaterialTheme.typography.titleMedium,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier
                        .padding(horizontal = 16.sdp)
                        .padding(top = 8.sdp)
                )
            }

            /*if (selectedProvinsi.isEmpty()) {
                LazyColumn {
                    items(listProvinsi, key = { it.id }) { provinsi ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedProvinsi = provinsi.name
                                    viewModel.getKabupatenKota(provinsi.id)
                                }
                                .padding(vertical = 8.sdp)

                        ) {
                            Text(
                                text = provinsi.name,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.sdp)
                            )
                        }
                        HorizontalDivider()
                    }
                }
            } else*/ if (selectedKabuptatenKota.isEmpty()) {
                LazyColumn {
                    items(listKabupatenKota, key = { it.id }) { kabupatenKota ->

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedKabuptatenKota = kabupatenKota.name
                                    viewModel.getKecamatan(kabupatenKota.id)
                                }
                                .padding(vertical = 8.sdp)

                        ) {
                            Text(
                                text = kabupatenKota.name,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.sdp)
                            )
                        }
                        HorizontalDivider()

                    }
                }
            } else if (selectedKecamatan.isEmpty()) {
                LazyColumn {
                    items(listKecamatan, key = { it.id }) { kecamatan ->
                        Text(
                            text = kecamatan.name,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedKecamatan = kecamatan.name
                                    viewModel.getKelurahan(kecamatan.id)
                                }
                                .padding(vertical = 8.sdp, horizontal = 16.sdp)
                        )
                        HorizontalDivider()

                    }
                }
            } else {
                LazyColumn {
                    items(listKelurahan, key = { it.id }) { kelurahan ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedKelurahan = kelurahan.name
                                }
                                .padding(horizontal = 16.sdp, vertical = 8.sdp)

                        ) {
                            Text(
                                text = kelurahan.name,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            if (selectedKelurahan == kelurahan.name) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MyStyle.colors.primaryMain,
                                modifier = Modifier.size(16.sdp)
                            )}
                        }
                        HorizontalDivider()

                    }
                }
            }
        }
    }
}