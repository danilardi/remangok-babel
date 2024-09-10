package com.ipb.remangokbabel.ui.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.model.component.ScreenType
import com.ipb.remangokbabel.model.response.DetailOrderedItem
import com.ipb.remangokbabel.model.response.DetailProduk
import com.ipb.remangokbabel.model.response.OrderedItem
import com.ipb.remangokbabel.ui.components.common.EmptyScreen
import com.ipb.remangokbabel.ui.components.product.ProductOrderCard
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun OrderListItemScreen(
    orderList: List<OrderedItem> = emptyList(),
    orderDetailData: List<DetailOrderedItem> = emptyList(),
    onUpdateData: () -> Unit = {},
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    if (orderList.isEmpty()) {
        EmptyScreen(type = ScreenType.Empty)
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MyStyle.colors.bgWhite)
                .verticalScroll(rememberScrollState())
        ) {
            orderList.map { order ->
                orderDetailData.find { it. == order.idProduk }?.let {
                    ProductOrderCard(
                        id = order.id,
                        detailOrder = it,
                        modifier = Modifier.padding(top = 8.sdp),
                        onUpdateData = onUpdateData
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}