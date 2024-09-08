package com.ipb.remangokbabel.ui.components.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.model.response.DetailProduk
import com.ipb.remangokbabel.model.response.OrderedItem
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.ConfirmDialog
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun ProductOrderCard(
    order: OrderedItem,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    var product by remember {
        mutableStateOf(
            DetailProduk(
                id = 1,
                nama = "Kepiting Bacang",
                gambar = listOf("kepiting"),
                hargaSatuan = 100000,
                berat = 100,
                deskripsi = "kepiting tak tau entah dimana",
                faseHidup = "telur",
                jumlahStok = 100,
                idOwner = "1",
                createdAt = "2021-08-01T00:00:00.000Z",
                updatedAt = "2021-08-02T00:00:00.000Z",
            )
        )
    }

    LaunchedEffect(Unit) {
        if (BuildConfig.DEBUG) {
            product = DetailProduk(
                id = 1,
                nama = "Kepiting Bacang Balikjaldhs fjhasdkhf ajshdfh ljsadhf",
                gambar = listOf("kepiting"),
                hargaSatuan = 100000,
                berat = 100,
                deskripsi = "kepiting tak tau entah dimana",
                faseHidup = "telur",
                jumlahStok = 100,
                idOwner = "1",
                createdAt = "2021-08-01T00:00:00.000Z",
                updatedAt = "2021-08-02T00:00:00.000Z",
            )
        }

        viewModel.getProduct(order.idProduk)

        coroutineScope.launch {
//            viewModel.getProductResponse.collect {
//                product = it.detailProductData.detailProduk
//            }
        }
    }

    if (showDialog) {
        ConfirmDialog(
            onDismiss = { showDialog = false }
        )
    }

    Card(
        onClick = { },
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
                Text(
                    text = order.idPembeli,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.sdp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = order.status ?: "Belum dikonfirmasi",
                )
            }
            Row {
//                AsyncImage(
//                    model = "${BuildConfig.BASE_URL}${product.gambar.first()}",
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(16.sdp)
//                        .size(120.sdp)
//                        .clip(RoundedCornerShape(8.sdp))
//                        .border(1.sdp, MyStyle.colors.disableBorder, RoundedCornerShape(8.sdp))
//                )
                Image(
                    painter = painterResource(id = R.drawable.kepiting),
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
                        text = product.nama,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = product.faseHidup,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MyStyle.colors.textGrey
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "x ${order.jumlahPesanan}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 8.sdp)
                            .align(Alignment.End)
                    )
                    Text(
                        text = product.hargaSatuan.toRupiah(),
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
                    text = product.hargaSatuan.toRupiah(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier
                )
            }
            HorizontalDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonCustom(
                    text = "Tolak Pesanan",
                    type = ButtonType.Danger,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.sdp, vertical = 8.sdp),
                ) {
                }
                ButtonCustom(
                    text = "Proses Pesanan",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.sdp, vertical = 8.sdp),
                ) {
                    showDialog = true
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun ProductOrderCardPreview() {
    val order = OrderedItem(
        id = "order-uBafvoKcXPxJxEniUOzle",
        idPenjual = "user-cd1ZN_-9pNVpRb7XXs75Z",
        idPembeli = "Pembeli",
        idProduk = 4,
        idProfile = "profile-WRxyAQfVXgM1qBhliGiLU",
        jumlahPesanan = 100,
        hargaSatuan = 100000,
        status = null,
        createdAt = "2024-09-01T15:59:14.025Z",
        updatedAt = "2024-09-01T15:59:14.025Z"
    )
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MyStyle.colors.bgSecondary
    ) {
        ProductOrderCard(
            order = order,
        )
    }
}

