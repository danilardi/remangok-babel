package com.ipb.remangokbabel.ui.screen.order

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import com.ipb.remangokbabel.model.request.UpdateTransactionRequest
import com.ipb.remangokbabel.model.response.DetailOrderedItem
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.ConfirmDialog
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.product.ProductOrderCard
import com.ipb.remangokbabel.ui.components.profile.ProfileAddressCard
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.screen.profile.MenuItem
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.OrderViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.navigateToBack
import com.ipb.remangokbabel.utils.openWhatsApp
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun OrderDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    orderData: DetailOrderedItem? = null,
    orderViewModel: OrderViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)
    var showLoading by remember { mutableStateOf(false) }

    var showDialogConfirm by remember { mutableStateOf(false) }
    var showDialogStatus by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            orderViewModel.deleteOrderResponse.collect {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                navigateToBack(navController)
            }
        }

        coroutineScope.launch {
            orderViewModel.updateTransactionResponse.collect {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                navigateToBack(navController)
            }
        }

        coroutineScope.launch {
            orderViewModel.showLoading.collect {
                showLoading = it
            }
        }
        coroutineScope.launch {
            orderViewModel.errorResponse.collect { errorResponse ->
                Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
                if (errorResponse.message == "token anda tidak valid") {
                    paperPrefs.deleteAllData()
                    navigateToAndMakeTop(navController, Screen.Login.route)
                }
            }
        }
    }

    if (showLoading) {
        LoadingDialog()
    }

    if (showDialogConfirm) {
        ConfirmDialog(
            onDismiss = { showDialogConfirm = false },
            onPrimaryClick = {
                when (showDialogStatus) {
                    "tolak" -> {
                        val data = UpdateTransactionRequest(
                            idOrder = orderData!!.id,
                            status = "ditolak"
                        )
                        orderViewModel.updateTransactions(data)
                    }

                    "hapus" -> {
                        orderViewModel.deleteOrder(orderData!!.id)
                    }

                    "proses" -> {
                        val data = UpdateTransactionRequest(
                            idOrder = orderData!!.id,
                            status = "diproses"
                        )
                        orderViewModel.updateTransactions(data)
                    }

                    "selesai" -> {
                        val data = UpdateTransactionRequest(
                            idOrder = orderData!!.id,
                            status = "diterima"
                        )
                        orderViewModel.updateTransactions(data)
                    }
                }
                showDialogConfirm = false
            }
        )
    }

    Scaffold(
        topBar = {
            BackTopBar(
                title = "Detail Order",
                onClickBackButton = {
                    navigateToBack(navController)
                }
            )
        },
        bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .shadow(
                        elevation = 10.sdp, // Adjust the elevation as needed
                        shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                        clip = false // Don't clip the content to the shape
                    )
                    .background(color = MyStyle.colors.bgWhite)
                    .padding(16.sdp)
            ) {
                if (paperPrefs.getRole() == "penjual") {
                    if (orderData?.status == null) {
                        ButtonCustom(
                            text = "Tolak Pesanan",
                            type = ButtonType.Danger,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.sdp),
                        ) {
                            showDialogStatus = "tolak"
                            showDialogConfirm = true
                        }
                        ButtonCustom(
                            text = "Proses Pesanan",
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.sdp),
                        ) {
                            showDialogStatus = "proses"
                            showDialogConfirm = true
                        }
                    }
                } else {
                    if (orderData?.status == null) {
                        ButtonCustom(
                            text = "Batalkan Pesanan",
                            type = ButtonType.Danger,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.sdp),
                        ) {
                            showDialogStatus = "hapus"
                            showDialogConfirm = true
                        }
                        ButtonCustom(
                            text = "Hubungi Penjual",
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.sdp),
                        ) {
                            val message =
                                "Halo, saya ${orderData?.dataPembeli?.namaDepan} ingin konfirmasi pesanan saya pada aplikasi Remangok Babel dengan nomor pesanan ${orderData?.id}"
                            openWhatsApp(
                                context = context,
                                phoneNumber = orderData?.dataPenjual?.nomorTelepon ?: "",
                                message = message
                            )
                        }
                    }
                    if (orderData?.status == "diproses") {
                        ButtonCustom(
                            text = "Selesaikan Pesanan",
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            showDialogStatus = "selesai"
                            showDialogConfirm = true
                        }
                    }

                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            Text(
                text = "Alamat Pengiriman",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(top = 16.sdp)
            )
            ProfileAddressCard(
                item = ProfilesItem(
                    id = "",
                    namaKelurahan = orderData?.dataPembeli?.namaKelurahan ?: "Testing",
                    namaKecamatan = orderData?.dataPembeli?.namaKecamatan ?: "Testing",
                    namaKabupatenKota = orderData?.dataPembeli?.namaKabupatenKota ?: "Testing",
                    namaProvinsi = orderData?.dataPembeli?.namaProvinsi ?: "Testing",
                    kodePos = orderData?.dataPembeli?.kodePos ?: "Testing",
                    namaDepan = orderData?.dataPembeli?.namaDepan ?: "testing",
                    namaBelakang = orderData?.dataPembeli?.namaBelakang ?: "testing",
                    nomorTelepon = orderData?.dataPembeli?.nomorTelepon ?: "testing",
                    alamat = orderData?.dataPembeli?.alamat ?: "testing",
                    createdAt = "",
                    updatedAt = ""
                )
            )
            ProductOrderCard(order = orderData!!, showButton = false, fromDetail = true)
            Text(
                text = "Butuh Bantuan?",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(top = 16.sdp)
            )
            MenuItem(
                title = "Hubungi Penjual",
                onClick = {
                    val message =
                        "Halo, saya ${orderData?.dataPembeli?.namaDepan} ingin konfirmasi pesanan saya pada aplikasi Remangok Babel dengan nomor pesanan ${orderData?.id}"
                    openWhatsApp(
                        context = context,
                        phoneNumber = orderData?.dataPenjual?.nomorTelepon ?: "",
                        message = message
                    )
                },
                modifier = Modifier
                    .padding(top = 2.sdp)
            )
        }
    }
}