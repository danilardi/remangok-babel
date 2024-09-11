package com.ipb.remangokbabel.ui.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.model.component.ScreenType
import com.ipb.remangokbabel.model.response.DetailOrderedItem
import com.ipb.remangokbabel.ui.components.common.EmptyScreen
import com.ipb.remangokbabel.ui.components.product.ProductOrderCard
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun OrderListItemScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    orderList: List<DetailOrderedItem> = emptyList(),
    onUpdateData: () -> Unit = {},
) {
    if (orderList.isEmpty()) {
        EmptyScreen(type = ScreenType.Empty)
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = MyStyle.colors.bgWhite)
        ) {
            items(orderList) {
                ProductOrderCard(
                    order = it,
                    modifier = Modifier.padding(top = 8.sdp),
                    onUpdateData = onUpdateData,
                    navController = navController
                )
                HorizontalDivider()
            }
        }
    }
}