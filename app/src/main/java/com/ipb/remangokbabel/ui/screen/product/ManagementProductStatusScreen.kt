package com.ipb.remangokbabel.ui.screen.product

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.ipb.remangokbabel.model.component.ScreenType
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.components.common.EmptyScreen
import com.ipb.remangokbabel.ui.components.product.ProductManagementCard
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.utils.navigateTo
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ManagementProductStatusScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    productList: List<ProductItem> = emptyList(),
    offset: Int = 0,
    onLastItemVisible: () -> Unit = {},
    onDeleteClick: (Int) -> Unit = {},
) {
    if (productList.isEmpty()) {
        EmptyScreen(
            type = ScreenType.Empty,
            title = "Belum memiliki produk\nSilahkan tambahkan produk anda",
        )
    } else {
        LazyColumn(
            modifier = Modifier.background(MyStyle.colors.layoutBorder)
        ) {
            itemsIndexed(productList, key = { _ , it -> it.id }) { index, product ->
                ProductManagementCard(
                    product = product,
                    onClickCardItem = {
                        val productData = Uri.encode(Gson().toJson(product))
                        navigateTo(navController, Screen.DetailProduct.createRoute(productData))
                    },
                    onEditClick = {
                        val data = Uri.encode(Gson().toJson(product))
                        navigateTo(navController, Screen.EditProduct.createRoute(data))
                    },
                    onDeleteClick = {
                        onDeleteClick(product.id)
                    },
                    modifier = Modifier.then(
                        if (index == productList.lastIndex) {
                            Modifier
                                .padding(bottom = 4.sdp)
                                .onGloballyPositioned {
                                    // Panggil ketika item terakhir terlihat
                                    onLastItemVisible()
                                }
                        } else Modifier
                            .padding(bottom = 4.sdp)
                    )
                )
            }
        }
    }
}