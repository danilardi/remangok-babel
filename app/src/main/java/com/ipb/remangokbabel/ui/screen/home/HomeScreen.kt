package com.ipb.remangokbabel.ui.screen.home

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.response.GetKecamatanResponseItem
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.EmptyScreen
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.common.SelectableDialog
import com.ipb.remangokbabel.ui.components.product.ProductCard
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.capitalizeEachWord
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    productViewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var listProduct by remember { mutableStateOf(emptyList<ProductItem>()) }
    val limit by remember { mutableIntStateOf(10) }
    var offset by remember { mutableIntStateOf(0) }
    var filter by remember { mutableStateOf("") }
    var onLoad by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var filterItem by remember { mutableStateOf(emptyList<GetKecamatanResponseItem>()) }

    LaunchedEffect(Unit) {
        productViewModel.getAllProducts(limit, offset, filter.capitalizeEachWord())
        profileViewModel.getKecamatan()
        onLoad = true
    }

    profileViewModel.getKecamatanState.collectAsState().value.let {
        if (it.isNotEmpty()) {
            filterItem = it
        }
        profileViewModel.clearKecamatanState()
    }

    productViewModel.productStateAll.collectAsState().value?.let {
        onLoad = false
        listProduct += it.data.produk
        productViewModel.clearProductStateALl()
    }

    profileViewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    productViewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    profileViewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
        profileViewModel.clearError()
    }

    productViewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            if (it.message == "token anda tidak valid") {
                paperPrefs.deleteAllData()
                navigateToAndMakeTop(navController, Screen.Login.route)
            }
        }
        productViewModel.clearError()
    }

    SelectableDialog(showDialog = showFilterDialog,
        selectableItems = filterItem.map { it.name.capitalizeEachWord() },
        selectedItem = filter,
        onDismissRequest = { showFilterDialog = false },
        onSelectedItemChange = {
            filter = it
            listProduct = emptyList()
            productViewModel.getAllProducts(limit, offset, filter.capitalizeEachWord())
        })

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Remangok Babel",
                modifier = if (BuildConfig.DEBUG) Modifier.clickable {
                } else Modifier
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MyStyle.colors.bgWhite)
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Produk Terbaru",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .weight(1f)
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.sdp))
                        .clickable {
                            showFilterDialog = true
                        }
                        .background(MyStyle.colors.bgSecondary)
                        .border(
                            width = 1.sdp,
                            color = MyStyle.colors.bgPrimary,
                            shape = RoundedCornerShape(16.sdp)
                        )
                        .padding(horizontal = 8.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterAlt,
                        contentDescription = "Filter",
                        tint = MyStyle.colors.textPrimary,
                    )
                    Text(
                        text = filter.ifEmpty { "Filter" },
                        color = MyStyle.colors.textPrimary,
                        modifier = Modifier.padding(8.sdp)
                    )
                }
                if (filter != "") {
                    Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "clear",
                    modifier = Modifier.clickable {
                        filter = ""
                        listProduct = emptyList()
                        productViewModel.getAllProducts(limit, offset, filter.capitalizeEachWord())
                    })
                }
            }
            ProductGrid(
                listProduct = listProduct,
                onViewDetailsClick = {
                    val productData = Uri.encode(Gson().toJson(it))
                    navigateTo(navController, Screen.DetailProduct.createRoute(productData))
                },
                onLastItemVisible = {
                    if (!onLoad && listProduct.size == offset + limit) {
                        onLoad = true
                        offset += limit
                        productViewModel.getAllProducts(limit, offset, filter.capitalizeEachWord())
                    }
                },
            )
        }
    }


}

@Composable
fun ProductGrid(
    listProduct: List<ProductItem>,
    modifier: Modifier = Modifier,
    onViewDetailsClick: (ProductItem) -> Unit = {},
    onLastItemVisible: () -> Unit = {}
) {
    if (listProduct.isEmpty()) {
        EmptyScreen()
    } else 
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        itemsIndexed(listProduct, key = { _, item -> item.id }) { index, product ->
            ProductCard(
                product = product,
                onViewDetailsClick = {
                    onViewDetailsClick(product)
                },
                modifier = Modifier.then(
                    if (index == listProduct.lastIndex) {
                        Modifier
                            .padding(bottom = 16.sdp)
                            .onGloballyPositioned {
                                // Panggil ketika item terakhir terlihat
                                onLastItemVisible()
                            }
                    } else Modifier
                )
            )
        }
    }
}


