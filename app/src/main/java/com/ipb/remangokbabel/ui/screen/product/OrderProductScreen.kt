package com.ipb.remangokbabel.ui.screen.product

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.AddOrderRequest
import com.ipb.remangokbabel.model.response.DetailProduk
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.profile.ProfileAddressCard
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.OrderViewModel
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.ui.viewmodel.ProfileViewModel
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.navigateToBack
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun OrderProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    productViewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    orderViewModel: OrderViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    productId: Int = -1,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = remember { PaperPrefs(context) }
    var showLoading by remember { mutableStateOf(false) }

    var product by remember { mutableStateOf(null as DetailProduk?) }
    var address by remember { mutableStateOf(emptyList<ProfilesItem>()) }
    var quantityProduct by remember { mutableIntStateOf(0) }

    var selectedAddress by remember { mutableStateOf(null as ProfilesItem?) }


    LaunchedEffect(Unit) {
        profileViewModel.getProfile()
        productViewModel.getProduct(productId)

        coroutineScope.launch {
            productViewModel.getProductResponse.collect {
                product = it.detailProductData.detailProduk
            }
        }

        coroutineScope.launch {
            profileViewModel.getProfileResponse.collect {
                address = it.data.profiles
            }
        }

        coroutineScope.launch {
            orderViewModel.createOrderResponse.collect {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                navigateToAndMakeTop(navController, Screen.Home.route)
            }
        }

        coroutineScope.launch {
            profileViewModel.showLoading.collect {
                showLoading = it
            }
        }

        coroutineScope.launch {
            productViewModel.showLoading.collect {
                showLoading = it
            }
        }

        coroutineScope.launch {
            orderViewModel.showLoading.collect {
                showLoading = it
            }
        }

        coroutineScope.launch {
            profileViewModel.errorResponse.collect { errorResponse ->
                Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
                if (errorResponse.message == "token anda tidak valid") {
                    paperPrefs.deleteAllData()
                    navigateToAndMakeTop(navController, Screen.Login.route)
                }
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

    if (showLoading) {
        LoadingDialog()
    }

    Scaffold(
        topBar = {
            BackTopBar(
                title = "Buat Pesanan Baru",
                onClickBackButton = { navigateToBack(navController) })
        },
        bottomBar = {
            ButtonCustom(
                text = "Buat Pesanan",
                enabled = selectedAddress != null && quantityProduct > 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.sdp, // Adjust the elevation as needed
                        shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                        clip = false // Don't clip the content to the shape
                    )
                    .background(color = MyStyle.colors.bgWhite)
                    .padding(16.sdp)
            ) {
                val request = AddOrderRequest(
                    idProduk = productId,
                    idProfile = selectedAddress!!.id,
                    jumlahPesanan = quantityProduct
                )
                orderViewModel.createOrder(request)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = "Pilih Alamat Pengiriman",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(top = 16.sdp)
            )
            LazyColumn() {
                items(address, key = { it.id }) { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                selectedAddress = item
                            }
                    ) {
                        ProfileAddressCard(
                            item = item,
                            modifier = Modifier.weight(1f)
                        ){ selectedAddress = item }
                        Box(
                            modifier = Modifier
                                .padding(end = 16.sdp)
                                .size(20.sdp)
                                .clip(CircleShape)
                                .background(MyStyle.colors.bgWhite)
                                .border(
                                    BorderStroke(2.sdp, MyStyle.colors.primaryMain),
                                    CircleShape
                                )
                                .padding(4.sdp)
                                .clip(CircleShape)
                                .background(if (selectedAddress == item) MyStyle.colors.primaryMain else MyStyle.colors.bgWhite)
                        )
                    }
                    HorizontalDivider(
                        thickness = 1.sdp,
                        color = MyStyle.colors.bgSecondary
                    )
                }
            }
            if (address.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.sdp, vertical = 8.sdp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Anda belum memiliki alamat",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 4.sdp)
                            .border(
                                BorderStroke(1.sdp, color = MyStyle.colors.primaryMain),
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .size(36.sdp)
                            .background(MyStyle.colors.primaryMain)
                            .clickable {
                                navigateTo(navController, Screen.AddProfile.route)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MyStyle.colors.bgWhite
                        )
                    }
                }
            }
            Text(
                text = "Produk yang Dipesan",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(top = 16.sdp)
            )
            Row {
                AsyncImage(
                    model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product!!.gambar.first()}" else "",
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.sdp)
                        .size(120.sdp)
                        .clip(RoundedCornerShape(8.sdp))
                        .border(1.sdp, MyStyle.colors.disableBorder, RoundedCornerShape(8.sdp))
                )
//                Image(
//                    painter = painterResource(id = R.drawable.kepiting),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(16.sdp)
//                        .size(120.sdp)
//                        .clip(RoundedCornerShape(8.sdp))
//                        .border(1.sdp, MyStyle.colors.disableBorder, RoundedCornerShape(8.sdp))
//                )
                Column(
                    modifier = Modifier
                        .padding(top = 16.sdp, end = 16.sdp, bottom = 16.sdp)
                        .fillMaxWidth()
                        .height(120.sdp),
                ) {
                    Text(
                        text = product?.nama ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    val fase =
                        if ((product?.faseHidup ?: "") == "dewasa") "Kepiting" else "Kepiting"
                    Text(
                        text = fase,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MyStyle.colors.textGrey
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = product?.hargaSatuan?.toRupiah() ?: 0.toRupiah(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 8.sdp)
                            .align(Alignment.End)
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 4.sdp)
                            .align(Alignment.End)
                    ) {
                        Text(
                            text = "Total: ",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                        )
                        Text(
                            text = ((product?.hargaSatuan ?: 0) * quantityProduct).toRupiah(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MyStyle.colors.textPrimary,
                            modifier = Modifier
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 16.sdp, vertical = 8.sdp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.sdp, color = MyStyle.colors.primaryMain),
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .size(36.sdp)
                        .clickable {
                            if (quantityProduct > 0)
                                quantityProduct--
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = null,
                        tint = MyStyle.colors.primaryMain
                    )
                }
                Text(
                    text = "$quantityProduct kg",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 16.sdp)
                )
                Box(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.sdp, color = MyStyle.colors.primaryMain),
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .size(36.sdp)
                        .background(MyStyle.colors.primaryMain)
                        .clickable {
                            quantityProduct++
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MyStyle.colors.bgWhite
                    )
                }
            }
        }
    }
}