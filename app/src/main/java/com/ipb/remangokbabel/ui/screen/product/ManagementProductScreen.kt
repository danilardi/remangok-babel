package com.ipb.remangokbabel.ui.screen.product

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.component.ScreenType
import com.ipb.remangokbabel.model.response.ProdukItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.EmptyScreen
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.product.ProductManagementCard
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.navigateTo
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun ManagementProductScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val listState = rememberLazyListState() // State untuk LazyColumn

    var showLoading by remember { mutableStateOf(false) }

    var offset by remember { mutableIntStateOf(0) }
    var productList by remember { mutableStateOf(emptyList<ProdukItem>()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.getAllProductsResponse.collect {
                productList += it.data.produk
            }
        }
        coroutineScope.launch {
            viewModel.deleteProductResponse.collect {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                productList = emptyList()
                if (offset == 0) {
                    viewModel.getAllProducts(10, offset)
                } else {
                    offset = 0
                }
            }
        }
        coroutineScope.launch {
            viewModel.showLoading.collect {
                showLoading = it
            }
        }
    }
    LaunchedEffect(offset) {
        viewModel.getAllProducts(10, offset)
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                // Jika posisi item terakhir terlihat mendekati akhir daftar, muat data lebih banyak
                if (lastVisibleItemIndex == productList.size - 1 && productList.size > offset && productList.size % 10 == 0) {
                    offset += 10
                }
            }
    }

    if (showLoading) {
        LoadingDialog()
    }

    Scaffold(
        topBar = {
            BackTopBar(title = "Manajemen Produk") {
                navController.popBackStack()
            }
        },
        bottomBar = {
            ButtonCustom(
                text = "Tambah Produk",
                modifier = Modifier.padding(8.sdp)
            ) {
                navigateTo(navController, Screen.AddProduct.route)
//                println("cekkk: ${productList.size}")
            }
        },
    ) { innerPadding ->
        if (productList.isEmpty()) {
            EmptyScreen(type = ScreenType.Empty)
        }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(innerPadding)
                .background(MyStyle.colors.bgSecondary)
        ) {
            items(productList, key = { it.id }) { product ->
                ProductManagementCard(
                    product = product,
                    onEditClick = {
                        navigateTo(navController, Screen.EditProduct.createRoute(product.id))
                    },
                    onDeleteClick = {
                        viewModel.deleteProduct(product.id)
                    },
                    modifier = Modifier.padding(vertical = 4.sdp)
                )
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