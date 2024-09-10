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
import com.ipb.remangokbabel.model.response.DetailProduk
import com.ipb.remangokbabel.model.response.OrderedItem
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    productViewModel: ProductViewModel = viewModel(
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
    var orderProductData by remember { mutableStateOf(emptyList<DetailProduk>()) }

    LaunchedEffect(Unit) {
        productViewModel.getOrders()

        coroutineScope.launch {
            productViewModel.getOrdersResponse.collect { response ->
                // filter order based on status
//                orderList = response.data.ordered.filter { it.status == status }
                orderList = response.data.ordered
            }
        }

        coroutineScope.launch {
            productViewModel.getProductResponse.collect { response ->
                orderProductData += response.detailProductData.detailProduk
            }
        }

        coroutineScope.launch {
            productViewModel.showLoading.collect {
                showLoading = it
            }
        }
        coroutineScope.launch {
            productViewModel.errorResponse.collect { errorResponse ->
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
            productViewModel.getProduct(order.idProduk)
        }
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
                        orderList = orderList.filter { it.status == null },
                        orderProductData = orderProductData,
                        navController = navController,
                        onUpdateData = {
                            orderProductData = emptyList()
                            productViewModel.getOrders()
                        }
                    )
                } else if (page == 1) {
                    OrderListItemScreen(
                        orderList = orderList.filter { it.status == "diproses" },
                        orderProductData = orderProductData,
                        navController = navController,
                        onUpdateData = {
                            orderProductData = emptyList()
                            productViewModel.getOrders()
                        }
                    )
                } else if (page == 2) {
                    OrderListItemScreen(
                        orderList = orderList.filter { it.status == "diterima" },
                        orderProductData = orderProductData,
                        navController = navController,
                        onUpdateData = {
                            orderProductData = emptyList()
                            productViewModel.getOrders()
                        }
                    )
                } else {
                    OrderListItemScreen(
                        orderList = orderList.filter { it.status == "ditolak" },
                        orderProductData = orderProductData,
                        navController = navController,
                        onUpdateData = {
                            orderProductData = emptyList()
                            productViewModel.getOrders()
                        }
                    )
                }
            }
        }
    }
}
