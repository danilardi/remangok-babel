package com.ipb.remangokbabel.ui.screen.order

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.response.DetailOrderedItem
import com.ipb.remangokbabel.model.response.OrderedItem
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.OrderViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    orderViewModel: OrderViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)
    var showLoading by remember { mutableStateOf(false) }


    var onSearch by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 4 })

    val tahapanOrder = listOf(
        if (paperPrefs.getRole() == "penjual") "Pesanan Masuk" else "Menunggu Konfirmasi",
        "Diproses",
        "Selesai",
        "Dibatalkan"
    )

    var orderList by remember { mutableStateOf(emptyList<OrderedItem>()) }
    var orderDetailData by remember { mutableStateOf(emptyList<DetailOrderedItem>()) }

    LaunchedEffect(Unit) {
        orderViewModel.getOrders()

        coroutineScope.launch {
            orderViewModel.getOrdersResponse.collect { response ->
                orderList = response.data.ordered
            }
        }

        coroutineScope.launch {
            orderViewModel.getDetailOrderResponse.collect { response ->
                orderDetailData += response.data.ordered
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


    LaunchedEffect(orderList) {
        orderList.forEach { order ->
            orderViewModel.getDetailOrder(order.id)
        }
    }

    if (showLoading) {
        LoadingDialog()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Penjualan Saya",
                modifier = Modifier
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MyStyle.colors.bgWhite)
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) {
                    onSearch = false
                },
        ) {
            LazyRow(
                modifier = Modifier
                    .background(MyStyle.colors.bgWhite)
            ) {
                itemsIndexed(tahapanOrder) { index, it ->
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleSmall,
                            color = if (pagerState.currentPage == index) MyStyle.colors.textPrimary else MyStyle.colors.textGrey,
                            modifier = Modifier
                                .padding(horizontal = 16.sdp, vertical = 8.sdp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
            HorizontalPager(
                state = pagerState,
            ) { page ->
                if (page == 0) {
                    OrderListItemScreen(
                        orderList = orderDetailData.filter { it.status == null },
                        navController = navController,
                        onUpdateData = {
                            orderDetailData = emptyList()
                            orderViewModel.getOrders()
                        }
                    )
                } else if (page == 1) {
                    OrderListItemScreen(
                        orderList = orderDetailData.filter { it.status == "diproses" },
                        navController = navController,
                        onUpdateData = {
                            orderDetailData = emptyList()
                            orderViewModel.getOrders()
                        }
                    )
                } else if (page == 2) {
                    OrderListItemScreen(
                        orderList = orderDetailData.filter { it.status == "diterima" },
                        navController = navController,
                        onUpdateData = {
                            orderDetailData = emptyList()
                            orderViewModel.getOrders()
                        }
                    )
                } else {
                    OrderListItemScreen(
                        orderList = orderDetailData.filter { it.status == "ditolak" },
                        navController = navController,
                        onUpdateData = {
                            orderDetailData = emptyList()
                            orderViewModel.getOrders()
                        }
                    )
                }
            }
        }
    }
}
