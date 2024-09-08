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
import com.ipb.remangokbabel.model.response.ErrorResponse
import com.ipb.remangokbabel.model.response.ProdukItem
import com.ipb.remangokbabel.ui.common.UiState
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.ProductCard
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

    Scaffold(
        topBar = {
            AppTopBar(title = "Remangok Babel", onSearch = onSearch, onSearchChange = { onSearch = it }, search = true)
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MyStyle.colors.bgWhite)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) {
                    onSearch = false
                }
        ) {
            /* Menu */
            /* Management Stok */
            ButtonCustom(
                text = "Management Stok",
                modifier = Modifier
                    .padding(horizontal = 16.sdp, vertical = 8.sdp)
            ) {
                navigateTo(navController, Screen.ManagementStock.route)
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
//                    LoadingDialog()
                    }

                    is UiState.Success -> {
                        ProductRow(listProduct = productState.data.data.produk)
                    }

                    is UiState.Error<*> -> {
                        val errorResponse = productState.errorData
                        if (errorResponse is ErrorResponse) {
                            Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT)
                                .show()
                            if (errorResponse.message == "token anda tidak valid") {
                                paperPrefs.deleteAllData()
                                navigateToAndMakeTop(navController, Screen.Auth.route)
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
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.sdp),
        modifier = modifier
    ) {
        items(listProduct, key = { it.id }) { product ->
            ProductCard(product = product)
        }
    }
}

