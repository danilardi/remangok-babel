package com.ipb.remangokbabel.ui.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.model.response.OrderedItem
import com.ipb.remangokbabel.ui.components.product.ProductOrderCard
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun OrderIncomingScreen(modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var orderList by remember { mutableStateOf(emptyList<OrderedItem>()) }

    LaunchedEffect(Unit) {
        orderList = listOf(
            OrderedItem(
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
            ),
            OrderedItem(
                id = "order1-uBafvoKcXPxJxEniUOzle",
                idPenjual = "user-cd1ZN_-9pNVpRb7XXs75Z",
                idPembeli = "Pembeli 2",
                idProduk = 4,
                idProfile = "profile-WRxyAQfVXgM1qBhliGiLU",
                jumlahPesanan = 100,
                hargaSatuan = 100000,
                status = null,
                createdAt = "2024-09-01T15:59:14.025Z",
                updatedAt = "2024-09-01T15:59:14.025Z"
            )
        )
    }


    LazyColumn(
        modifier = modifier
            .background(color = MyStyle.colors.bgSecondary)
    ) {
        items(orderList, key = { it.id }) { order ->
            ProductOrderCard(
                order = order,
                modifier = Modifier.padding(top = 8.sdp)
            )
        }
    }
}