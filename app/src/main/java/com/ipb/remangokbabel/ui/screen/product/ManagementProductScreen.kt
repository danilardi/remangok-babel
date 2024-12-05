package com.ipb.remangokbabel.ui.screen.product

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.capitalizeEachWord
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ManagementProductScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)
    val coroutineScope = rememberCoroutineScope()

    var onLoad by remember { mutableStateOf(false) }

    val statusProduct = listOf(
        "requested",
        "accepted",
        "rejected",
    )

    val statusProductTitle = listOf(
        "Konfirmasi",
        "Diterima",
        "Ditolak",
    )

    var productList by remember { mutableStateOf<List<ProductItem>>(emptyList()) }

    var productOnDelete by remember { mutableIntStateOf(0) }

    val offset = remember {
        mutableMapOf(
            statusProduct[0] to 0,
            statusProduct[1] to 0,
            statusProduct[2] to 0,
        )
    }

    LaunchedEffect(Unit) {
        if (paperPrefs.getRole() == "admin") {
            viewModel.getAllProductsByAdmin(10, offset[statusProduct[0]] ?: 0, statusProduct[0])
            viewModel.getAllProductsByAdmin(10, offset[statusProduct[1]] ?: 0, statusProduct[1])
            viewModel.getAllProductsByAdmin(10, offset[statusProduct[2]] ?: 0, statusProduct[2])
        } else {
            viewModel.getSelfProducts(10, offset[statusProduct[0]] ?: 0, statusProduct[0])
            viewModel.getSelfProducts(10, offset[statusProduct[1]] ?: 0, statusProduct[1])
            viewModel.getSelfProducts(10, offset[statusProduct[2]] ?: 0, statusProduct[2])
        }
    }

    LaunchedEffect(productList) {

    }

    viewModel.productStateRequested.collectAsState().value?.let {
        productList += it.data.produk
        viewModel.clearProductStateRequested()
        onLoad = false
    }

    viewModel.productStateAccepted.collectAsState().value?.let {
        productList += it.data.produk
        viewModel.clearProductStateAccepted()
        onLoad = false
    }

    viewModel.productStateRejected.collectAsState().value?.let {
        productList += it.data.produk
        viewModel.clearProductStateRejected()
        onLoad = false
    }

    viewModel.deleteProductState.collectAsState().value?.let {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        productList = productList.filter { product -> product.id != productOnDelete }
        viewModel.clearDeleteProductState()
    }

    viewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    viewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            if (it.message == "token anda tidak valid") {
                paperPrefs.deleteAllData()
                navigateToAndMakeTop(navController, Screen.Login.route)
            }
        }
        viewModel.clearError()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Manajemen Produk",
                onClickToProfile = {
                navigateToAndMakeTop(navController, Screen.Setting.route)
                },
                isManagementProdcut = false,
                )
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.sdp)
                        .shadow(
                            elevation = 4.sdp,   // Atur ketinggian bayangan
                            shape = RectangleShape, // Bentuk bayangan persegi (atau bisa RoundedCornerShape)
                            clip = false           // Agar konten tidak terpotong mengikuti shape
                        )
                        .background(MyStyle.colors.bgWhite)
                ) {
                    statusProductTitle.forEachIndexed { index, it ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                        ) {
                            Text(
                                text = it.capitalizeEachWord(),
                                style = MaterialTheme.typography.titleSmall,
                                color = if (pagerState.currentPage == index) MyStyle.colors.textPrimary else MyStyle.colors.textGrey,
                                modifier = Modifier
                                    .padding(vertical = 16.sdp)
                                    .align(Alignment.Center)
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.sdp)
                                    .background(if (pagerState.currentPage == index) MyStyle.colors.textPrimary else MyStyle.colors.bgWhite)
                                    .align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
                HorizontalPager(
                    state = pagerState,
                ) { page ->
                    when (page) {
                        0 -> {
                            ManagementProductStatusScreen(
                                navController = navController,
                                productList = productList.filter { it.status == statusProduct[0] },
                                onDeleteClick = { id ->
                                    productOnDelete = id
                                    viewModel.deleteProduct(id)
                                },
                                onLastItemVisible = {
                                    if (!onLoad && productList.filter { it.status == statusProduct[0] }.size % 10 == 0) {
                                        onLoad = true
                                        offset[statusProduct[0]] =
                                            offset[statusProduct[0]]?.plus(10) ?: 0
                                        if (paperPrefs.getRole() == "admin") {
                                            viewModel.getAllProductsByAdmin(
                                                10,
                                                offset[statusProduct[0]] ?: 0,
                                                statusProduct[0]
                                            )
                                        } else {
                                            viewModel.getSelfProducts(
                                                10,
                                                offset[statusProduct[0]] ?: 0,
                                                statusProduct[0]
                                            )
                                        }
                                    }
                                }
                            )
                        }

                        1 -> {
                            ManagementProductStatusScreen(
                                navController = navController,
                                productList = productList.filter { it.status == statusProduct[1] },
                                onDeleteClick = { id ->
                                    productOnDelete = id
                                    viewModel.deleteProduct(id)
                                },
                                onLastItemVisible = {
                                    if (!onLoad && productList.filter { it.status == statusProduct[1] }.size % 10 == 0) {
                                        onLoad = true
                                        offset[statusProduct[1]] =
                                            offset[statusProduct[1]]?.plus(10) ?: 0
                                        if (paperPrefs.getRole() == "admin") {
                                            viewModel.getAllProductsByAdmin(
                                                10,
                                                offset[statusProduct[1]] ?: 0,
                                                statusProduct[1]
                                            )
                                        } else {
                                            viewModel.getSelfProducts(
                                                10,
                                                offset[statusProduct[1]] ?: 0,
                                                statusProduct[1]
                                            )
                                        }
                                    }
                                }
                            )
                        }

                        2 -> {
                            ManagementProductStatusScreen(
                                navController = navController,
                                productList = productList.filter { it.status == statusProduct[2] },
                                onDeleteClick = { id ->
                                    productOnDelete = id
                                    viewModel.deleteProduct(id)
                                },
                                onLastItemVisible = {
                                    if (!onLoad && productList.filter { it.status == statusProduct[2] }.size % 10 == 0) {
                                        onLoad = true
                                        offset[statusProduct[2]] =
                                            offset[statusProduct[2]]?.plus(10) ?: 0
                                        if (paperPrefs.getRole() == "admin") {
                                            viewModel.getAllProductsByAdmin(
                                                10,
                                                offset[statusProduct[2]] ?: 0,
                                                statusProduct[2]
                                            )
                                        } else {
                                            viewModel.getSelfProducts(
                                                10,
                                                offset[statusProduct[2]] ?: 0,
                                                statusProduct[2]
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
            if (paperPrefs.getRole() != "admin") FloatingActionButton(
                onClick = {
                    navigateTo(navController, Screen.AddProduct.route)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.sdp),
                containerColor = MyStyle.colors.bgSecondary,
                contentColor = MyStyle.colors.textBlack
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun ManagementProductScreenPreview() {
    val navController: NavHostController = rememberNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ManagementProductScreen(
            navController = navController,
        )
    }

}