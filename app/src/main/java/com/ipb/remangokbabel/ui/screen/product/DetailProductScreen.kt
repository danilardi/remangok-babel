package com.ipb.remangokbabel.ui.screen.product

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.ipb.remangokbabel.model.response.DetailProduk
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.navigateToBack
import com.ipb.remangokbabel.utils.toRupiah2
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun DetailProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    productId: Int = -1,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)
    var showLoading by remember { mutableStateOf(false) }

    var product by remember { mutableStateOf(null as DetailProduk?) }

    LaunchedEffect(Unit) {
        viewModel.getProduct(productId)

        coroutineScope.launch {
            viewModel.getProductResponse.collect {
                product = it.detailProductData.detailProduk
            }
        }

        coroutineScope.launch {
            viewModel.showLoading.collect {
                showLoading = it
            }
        }

        coroutineScope.launch {
            viewModel.errorResponse.collect { errorResponse ->
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
            BackTopBar(title = "Detail Produk", onClickBackButton = {
                navigateToBack(navController)
            }
            )
        },
        bottomBar = {
            ButtonCustom(
                text = "Pesan Produk",
                modifier = Modifier
                    .shadow(
                        elevation = 10.sdp, // Adjust the elevation as needed
                        shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                        clip = false // Don't clip the content to the shape
                    )
                    .background(color = MyStyle.colors.bgWhite)
                    .padding(16.sdp)
            ) {
                navigateTo(navController, Screen.AddOrder.createRoute(productId))
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            val pagerState = rememberPagerState(pageCount = { product?.gambar?.size ?: 0 })
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
            ) { page ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product!!.gambar[page]}" else "",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(30.sdp)
                    )
                    AsyncImage(
                        model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product!!.gambar[page]}" else "",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
            LazyRow(
                Modifier
                    .padding(top = 8.sdp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                items(pagerState.pageCount) { iteration ->
                    Box(modifier = Modifier
                        .padding(horizontal = 4.sdp)
                        .padding(
                            start = if (iteration == 0) 8.sdp else 0.sdp,
                            end = if (iteration == pagerState.pageCount - 1) 8.sdp else 0.sdp
                        )
                        .size(48.sdp)
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(iteration)
                            }
                        }
                    ) {
                        AsyncImage(
                            model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product!!.gambar[iteration]}" else "",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(30.sdp)
                        )
                        AsyncImage(
                            model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product!!.gambar[iteration]}" else "",
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(horizontal = 16.sdp)
            ) {
                Text(
                    text = "Rp",
                    style = MaterialTheme.typography.titleSmall,
                    color = MyStyle.colors.textPrimary
                )
                Text(
                    text = product?.hargaSatuan?.toRupiah2() ?: 0.toRupiah2(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )
                val fase = if (product?.faseHidup == "dewasa") "Kepiting" else "Benih"
                Text(
                    text = fase,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Text(
                text = product?.nama ?: "",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.sdp, vertical = 8.sdp)
            )
            HorizontalDivider()
            Text(
                text = "Detail Produk",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(horizontal = 16.sdp, vertical = 8.sdp)
            )
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.sdp, bottom = 8.sdp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Text(text = "Penjual : ", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = product?.dataPenjual?.nama ?: "",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        val fase =
                            if ((product?.faseHidup ?: "") == "dewasa") "Kepiting" else "Benih"
                        Text(text = "Fase      : ", style = MaterialTheme.typography.titleSmall)
                        Text(text = fase, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.sdp, bottom = 8.sdp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        val grade =
                            if ((product?.berat ?: 0) >= 500) "A"
                            else if ((product?.berat ?: 0) >= 200) "B"
                            else "C"
                        Text(text = "Grade : ", style = MaterialTheme.typography.titleSmall)
                        Text(text = grade, style = MaterialTheme.typography.bodySmall)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Text(text = "Berat  : ", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = (product?.berat ?: 0).toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            HorizontalDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.sdp, vertical = 8.sdp)
            ) {
                Text(
                    text = "Deskripsi Produk",
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = product?.dataPenjual?.nama ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 4.sdp)
                        .size(24.sdp)
                )
            }
            Text(
                text = product?.deskripsi ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MyStyle.colors.textGrey,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.sdp)
                    .padding(bottom = 36.sdp)
            )
        }
    }
}