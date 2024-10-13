package com.ipb.remangokbabel.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.response.DataProfile
import com.ipb.remangokbabel.model.response.ErrorResponse
import com.ipb.remangokbabel.model.response.ProdukItem
import com.ipb.remangokbabel.ui.common.UiState
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.product.ProductCard
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.HomeViewModel
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MyStyle.colors.bgWhite)
    systemUiController.setNavigationBarColor(color = MyStyle.colors.bgSecondary)
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var onSearch by remember { mutableStateOf(false) }
    var profileData by remember { mutableStateOf(null as DataProfile?) }

    LaunchedEffect(true) {
        viewModel.getProfile()
    }

    LaunchedEffect(profileData) {
        if (profileData != null && paperPrefs.getRole() == "penjual") {
            if (profileData?.profiles.isNullOrEmpty()) {
                Toast.makeText(context, "Anda belum memiliki profile\nSilahkan Tambahkan terlebih dahulu", Toast.LENGTH_SHORT).show()
                navigateTo(navController, Screen.ListProfile.route)
            }
        }
    }

    viewModel.profileState.collectAsState(initial = UiState.Idle).value.let { profileState ->
        when (profileState) {
            is UiState.Idle -> {
                viewModel.getAllProducts()
            }

            is UiState.Loading -> {
                LoadingDialog()
            }

            is UiState.Success -> {
                profileData = profileState.data.data
            }

            is UiState.Error<*> -> {
                val errorResponse = profileState.errorData
                if (errorResponse is ErrorResponse) {
                    Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT)
                        .show()
                    if (errorResponse.message == "token anda tidak valid") {
                        paperPrefs.deleteAllData()
                        navigateToAndMakeTop(navController, Screen.Login.route)
                    } else {
                        viewModel.getAllProducts()
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Remangok Babel",
                onClickIcon = {

                },
                icon = true
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
                }
        ) {
            /* Menu */
            /* Management Stok */
            if (paperPrefs.getRole() == "penjual") {
                ButtonCustom(
                    text = "Management Stok",
                    modifier = Modifier
                        .padding(horizontal = 16.sdp, vertical = 8.sdp)
                ) {

                    navigateTo(navController, Screen.ManagementStock.route)
                }
            }

            Text(
                text = "Produk",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
            )

            viewModel.productState.collectAsState(initial = UiState.Idle).value.let { productState ->
                when (productState) {
                    is UiState.Idle -> {
                        viewModel.getAllProducts()
                    }

                    is UiState.Loading -> {
                        LoadingDialog()
                    }

                    is UiState.Success -> {
                        ProductRow(
                            listProduct = productState.data.data.produk,
                            onViewDetailsClick = {
                                navigateTo(navController, Screen.DetailProduct.createRoute(it))
                            })
                    }

                    is UiState.Error<*> -> {
                        val errorResponse = productState.errorData
                        if (errorResponse is ErrorResponse) {
                            Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT)
                                .show()
                            if (errorResponse.message == "token anda tidak valid") {
                                paperPrefs.deleteAllData()
                                navigateToAndMakeTop(navController, Screen.Login.route)
                            } else {
                                viewModel.getAllProducts()
                            }
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun ProductRow(
    listProduct: List<ProdukItem>,
    onViewDetailsClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.sdp),
        modifier = modifier
    ) {
        items(listProduct, key = { it.id }) { product ->
            ProductCard(
                product = product,
                onViewDetailsClick = { onViewDetailsClick(product.id) }
            )
        }
    }
}

