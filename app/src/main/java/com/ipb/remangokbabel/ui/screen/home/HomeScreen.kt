package com.ipb.remangokbabel.ui.screen.home

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.product.ProductCard
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
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
    var limit by remember { mutableIntStateOf(10) }
    var offset by remember { mutableIntStateOf(0) }
    var onLoad by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        productViewModel.getAllProducts(limit, offset)
        profileViewModel.getProfile()
        onLoad = true
    }

    profileViewModel.getProfileState.collectAsState().value?.let {
        LaunchedEffect(Unit) {
            paperPrefs.setProfile(it.data.profiles)
        }
    }

    productViewModel.productStateAll.collectAsState().value?.let {
        onLoad = false
        listProduct += it.data.produk
        productViewModel.clearProductStateALl()
    }

    productViewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
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

    profileViewModel.showLoading.collectAsState().value.let {
        if (it) LoadingDialog()
    }

    profileViewModel.errorResponse.collectAsState().value.let {
        if (it.message?.isNotEmpty() == true) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            if (it.message == "token anda tidak valid") {
                paperPrefs.deleteAllData()
                navigateToAndMakeTop(navController, Screen.Login.route)
            }
        }
        profileViewModel.clearError()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Remangok Babel",
                modifier = if (BuildConfig.DEBUG) Modifier.clickable {
                    println("cekk ${listProduct.size} $listProduct")
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
                        productViewModel.getAllProducts(limit, offset)
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


