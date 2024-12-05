package com.ipb.remangokbabel.ui.components.product

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.model.request.UpdateTransactionRequest
import com.ipb.remangokbabel.model.response.DetailOrderedItem
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.ConfirmDialog
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.OrderViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.openWhatsApp
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ProductOrderCard(
    modifier: Modifier = Modifier,
    order: DetailOrderedItem? = null,
    showButton: Boolean = true,
    navController: NavHostController = rememberNavController(),
    viewModel: OrderViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    fromDetail: Boolean = false,
    onUpdateData: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var showLoading by remember { mutableStateOf(false) }

    var showDialogConfirm by remember { mutableStateOf(false) }
    var showDialogStatus by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.deleteOrderResponse.collect {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                onUpdateData()
            }
        }

        coroutineScope.launch {
            viewModel.updateTransactionResponse.collect {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                onUpdateData()
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

    if (showDialogConfirm) {
        ConfirmDialog(
            onDismiss = { showDialogConfirm = false },
            onPrimaryClick = {
                if (order != null)
                    when (showDialogStatus) {
                        "tolak" -> {
                            val data = UpdateTransactionRequest(
                                idOrder = order.id,
                                status = "ditolak"
                            )
                            viewModel.updateTransactions(data)
                        }

                        "hapus" -> {
                            viewModel.deleteOrder(order.id)
                        }

                        "proses" -> {
                            val data = UpdateTransactionRequest(
                                idOrder = order.id,
                                status = "diproses"
                            )
                            viewModel.updateTransactions(data)
                        }

                        "selesai" -> {
                            val data = UpdateTransactionRequest(
                                idOrder = order.id,
                                status = "diterima"
                            )
                            viewModel.updateTransactions(data)
                        }
                    }
                showDialogConfirm = false
            }
        )
    }

    if (showLoading) {
        LoadingDialog()
    }
    Card(
        onClick = {
            if (fromDetail) {
//                if (order != null)
//                    navigateTo(navController, Screen.DetailProduct.createRoute(order.produk.id))
            } else {
                val data = Uri.encode(Gson().toJson(order))
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MyStyle.colors.bgWhite,
        ),
        shape = RoundedCornerShape(0.sdp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.sdp, vertical = 8.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.sdp)
                )
                val name = if (paperPrefs.getRole() == "penjual") {
                    "${order?.dataPembeli?.namaDepan ?: ""} ${order?.dataPembeli?.namaBelakang ?: ""}"
                } else {
                    order?.dataPenjual?.fullname ?: ""
                }
                Text(
                    text = name,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.sdp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                val status = when (order?.status ?: "") {
                    "diproses" -> "Sedang Diproses"
                    "diterima" -> "Pesanan Selesai"
                    "ditolak" -> "Pesanan Dibatalkan"
                    else -> "Belum dikonfirmasi"
                }
                Text(
                    text = status,
                )
            }
            Row {
                AsyncImage(
                    model = "${BuildConfig.BASE_URL}${order?.produk?.gambar?.first()}",
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.sdp)
                        .size(120.sdp)
                        .clip(RoundedCornerShape(8.sdp))
                        .border(1.sdp, MyStyle.colors.disableBorder, RoundedCornerShape(8.sdp))
                )
                Column(
                    modifier = Modifier
                        .padding(top = 16.sdp, end = 16.sdp, bottom = 16.sdp)
                        .fillMaxWidth()
                        .height(120.sdp),
                ) {
                    Text(
                        text = order?.produk?.nama ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = order?.produk?.faseHidup ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MyStyle.colors.textGrey
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Quantity : x ${order?.jumlahPesanan ?: 0}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 8.sdp)
                            .align(Alignment.End)
                    )
                    Text(
                        text = order?.produk?.hargaSatuan?.toRupiah() ?: 0.toRupiah(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 8.sdp)
                            .align(Alignment.End)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.sdp, vertical = 8.sdp)
                    .align(Alignment.End),
            ) {
                Text(
                    text = "Total: ",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )
                Text(
                    text = ((order?.produk?.hargaSatuan ?: 0) * (order?.jumlahPesanan
                        ?: 0)).toRupiah(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier
                )
            }
            HorizontalDivider()
            if (showButton)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (paperPrefs.getRole() == "penjual") {
                        if (order?.status == null) {
                            ButtonCustom(
                                text = "Tolak Pesanan",
                                type = ButtonType.Danger,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 8.sdp)
                                    .padding(start = 8.sdp, end = 4.sdp),
                            ) {
                                showDialogStatus = "tolak"
                                showDialogConfirm = true
                            }
                            ButtonCustom(
                                text = "Proses Pesanan",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 8.sdp)
                                    .padding(start = 4.sdp, end = 8.sdp),
                            ) {
                                showDialogStatus = "proses"
                                showDialogConfirm = true
                            }
                        }
                    } else {
                        if (order?.status == null) {
                            ButtonCustom(
                                text = "Batalkan Pesanan",
                                type = ButtonType.Danger,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 8.sdp)
                                    .padding(start = 8.sdp, end = 4.sdp),
                            ) {
                                showDialogStatus = "hapus"
                                showDialogConfirm = true
                            }
                            ButtonCustom(
                                text = "Hubungi Penjual",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 8.sdp)
                                    .padding(start = 4.sdp, end = 8.sdp),
                            ) {
                                val message =
                                    "Halo, saya ${order?.dataPembeli?.namaDepan ?: ""} ingin konfirmasi pesanan saya pada aplikasi Remangok Babel dengan nomor pesanan ${order?.id ?: ""}"
                                openWhatsApp(
                                    context = context,
                                    phoneNumber = order?.dataPenjual?.nomorTelepon ?: "",
                                    message = message
                                )
                            }
                        }
                        if (order?.status == "diproses") {
                            ButtonCustom(
                                text = "Selesaikan Pesanan",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.sdp, vertical = 8.sdp),
                            ) {
                                showDialogStatus = "selesai"
                                showDialogConfirm = true
                            }
                        }

                    }
                }
        }
    }
}

