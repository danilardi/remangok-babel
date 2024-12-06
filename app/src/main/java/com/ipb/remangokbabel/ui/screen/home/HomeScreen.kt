package com.ipb.remangokbabel.ui.screen.home

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.response.GetKabupatenKotaResponseItem
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
import ir.kaaveh.sdpcompose.ssp

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
    var filterByKotaKabupaten by remember { mutableStateOf("") }
    var filterByKecamatan by remember { mutableStateOf("") }
    var onLoad by remember { mutableStateOf(false) }
    var showFilterByKotaKabupatenDialog by remember { mutableStateOf(false) }
    var showFilterByKecamatanDialog by remember { mutableStateOf(false) }
    var filterKotaKabupaten by remember {
        mutableStateOf(emptyList<GetKabupatenKotaResponseItem>())
    }
    var filterKecamatan by remember { mutableStateOf(emptyList<GetKecamatanResponseItem>()) }

    LaunchedEffect(Unit) {
        productViewModel.getAllProducts(limit, offset, filterByKotaKabupaten.capitalizeEachWord(), filterByKecamatan.capitalizeEachWord())
        profileViewModel.getKotaKabupaten()
        onLoad = true
    }

    profileViewModel.getKotaKabupatenState.collectAsState().value.let {
        if (it.isNotEmpty()) {
            filterKotaKabupaten = it
        }
        profileViewModel.cleanKotaKabupatenState()
    }

    profileViewModel.getKecamatanState.collectAsState().value.let {
        if (it.isNotEmpty()) {
            filterKecamatan = it
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

    SelectableDialog(showDialog = showFilterByKotaKabupatenDialog,
        selectableItems = filterKotaKabupaten.map { it.name.capitalizeEachWord() },
        selectedItem = filterByKotaKabupaten,
        onDismissRequest = { showFilterByKotaKabupatenDialog = false },
        onSelectedItemChange = {
            filterByKotaKabupaten = it
            listProduct = emptyList()
            offset = 0

            val idKotaKabupaten = filterKotaKabupaten.find {e -> e.name.capitalizeEachWord() == filterByKotaKabupaten.capitalizeEachWord()  }?.id
            profileViewModel.getKecamatan("$idKotaKabupaten")
            productViewModel.getAllProducts(limit, offset,filterByKotaKabupaten.capitalizeEachWord(), filterByKecamatan.capitalizeEachWord())
        })

    SelectableDialog(showDialog = showFilterByKecamatanDialog,
        selectableItems = filterKecamatan.map { it.name.capitalizeEachWord() },
        selectedItem = filterByKecamatan,
        onDismissRequest = { showFilterByKecamatanDialog = false },
        onSelectedItemChange = {
            filterByKecamatan = it
            listProduct = emptyList()
            offset = 0
            productViewModel.getAllProducts(limit, offset,filterByKotaKabupaten.capitalizeEachWord(), filterByKecamatan.capitalizeEachWord())
        })

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Remangok Babel",
                onClickToProfile = {
                    navigateTo(navController, Screen.Setting.route)
                },
                onClickToManagementProduct = {
                    navigateTo(navController, Screen.ManagementProduct.route)
                },

                modifier = Modifier.background(MyStyle.colors.neutral20)
            )
        },
        modifier = modifier.background(MyStyle.colors.bgWhite)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MyStyle.colors.neutral20)
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(top = 10.sdp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Produk Terbaru",
                    fontWeight = FontWeight.W600,
                    fontSize = 10.ssp,
                    modifier = Modifier
                        .weight(1f)
                )

                Column(
                   horizontalAlignment = Alignment.End
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.sdp))
                            .clickable {
                                showFilterByKotaKabupatenDialog = true
                            }
                            .background(MyStyle.colors.bgWhite)
                            .border(
                                width = 1.sdp,
                                color = MyStyle.colors.bgBlack,
                                shape = RoundedCornerShape(4.sdp)
                            )
                            .padding(horizontal = 8.sdp, vertical = 4.sdp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = filterByKotaKabupaten.ifEmpty { "Kota/Kabupaten" },
                            color = if (filterByKotaKabupaten.isEmpty()) MyStyle.colors.neutral600 else MyStyle.colors.textPrimary,
                            fontSize = 10.ssp,
                            fontWeight = FontWeight.W400,
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Filter",
                            tint = if (filterByKotaKabupaten.isEmpty()) MyStyle.colors.neutral600 else MyStyle.colors.textPrimary,
                            modifier = Modifier
                                .padding(start = 4.sdp)
                                .size(12.sdp)
                        )
                    }
                    Spacer(modifier = modifier.size(width = 0.sdp, height = 4.sdp))
                    if (filterByKotaKabupaten != "") {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.sdp))
                                .clickable {
                                    showFilterByKecamatanDialog = true
                                }
                                .background(MyStyle.colors.bgWhite)
                                .border(
                                    width = 1.sdp,
                                    color = MyStyle.colors.bgBlack,
                                    shape = RoundedCornerShape(4.sdp)
                                )
                                .padding(horizontal = 8.sdp, vertical = 4.sdp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = filterByKecamatan.ifEmpty { "Kecamatan" },
                                color = if (filterByKecamatan.isEmpty()) MyStyle.colors.neutral600 else MyStyle.colors.textPrimary,
                                fontSize = 10.ssp,
                                fontWeight = FontWeight.W400,
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Filter",
                                tint = if (filterByKecamatan.isEmpty()) MyStyle.colors.neutral600 else MyStyle.colors.textPrimary,
                                modifier = Modifier
                                    .padding(start = 4.sdp)
                                    .size(12.sdp)
                            )
                        }
                    }
                }
                if (filterByKecamatan != "") {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "clear",
                        modifier = Modifier.clickable {
                            filterByKotaKabupaten = ""
                            filterByKecamatan = ""
                            offset = 0
                            listProduct = emptyList()
                            productViewModel.getAllProducts(
                                limit,
                                offset,
                                filterByKotaKabupaten.capitalizeEachWord(),
                                filterByKecamatan.capitalizeEachWord()
                            )
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
                        productViewModel.getAllProducts(limit, offset, filterByKotaKabupaten.capitalizeEachWord(), filterByKecamatan.capitalizeEachWord())
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
            contentPadding = PaddingValues(start = 16.sdp, end = 16.sdp, bottom = 24.sdp),
            verticalArrangement = Arrangement.spacedBy(12.sdp),
            horizontalArrangement = Arrangement.spacedBy(10.sdp),
            modifier = modifier.padding(top = 16.sdp)
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
                                .onGloballyPositioned {
                                    onLastItemVisible()
                                }
                        } else Modifier
                    )
                )
            }
        }
}


