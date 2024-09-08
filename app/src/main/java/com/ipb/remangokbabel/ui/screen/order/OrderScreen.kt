package com.ipb.remangokbabel.ui.screen.order

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    var onSearch by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 4 })

    val tahapanOrder = listOf("Pesanan Masuk", "Diproses", "Selesai", "Dibatalkan")

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Penjualan Saya",
                search = true,
                onSearch = false,
                onSearchChange = { },
                modifier = Modifier
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MyStyle.colors.bgWhite)
                .padding(innerPadding)
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
                    OrderIncomingScreen()
                } else if (page == 1) {
                    OrderProcessingScreen()
                } else if (page == 2) {
                    OrderCompleteScreen()
                } else {
                    OrderRejectScreen()
                }
            }
        }
    }
}
