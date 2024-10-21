package com.ipb.remangokbabel.ui.screen.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.ipb.remangokbabel.model.request.VerifyProductRequest
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.capitalizeEachWord
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
    product: ProductItem? = null,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)
//    val profileData by remember { mutableStateOf(paperPrefs.getProfile()) }

    val showDialogVerifyProductItem by remember { mutableStateOf(false) }
    val verifyProductItems = listOf("requested", "accepted", "rejected")
    val selectedVerifyStatus by remember { mutableStateOf(product?.status ?: verifyProductItems[0]) }
    val alasanPenolakan by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            BackTopBar(
                title = "Detail Produk",
                onClickBackButton = {
                    navigateToBack(navController)
                }
            )
        },
        bottomBar = {
            if (paperPrefs.getRole() == "user") {
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
//                    val message =
//                        "Halo, saya ${profileData.dataDiri.fullname} ingin memesan ${product?.nama}"
//                    openWhatsApp(
//                        context = context,
//                        phoneNumber = product?.dataPemilik?.nomorTelepon ?: "",
////                        message = message
//                    )
                }
            } else if (paperPrefs.getRole() == "admin") {
                ButtonCustom(
                    text = "Verifikasi",
                    enabled = false,
                    modifier = Modifier
                        .shadow(
                            elevation = 10.sdp, // Adjust the elevation as needed
                            shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                            clip = false // Don't clip the content to the shape
                        )
                        .background(color = MyStyle.colors.bgWhite)
                        .padding(16.sdp)
                ) {
                    val data = VerifyProductRequest(
                        idProduk = "${product?.id ?: -1}",
                        status = selectedVerifyStatus,
                        alasanPenolakan = alasanPenolakan
                    )
                    viewModel.verifyProduct(data)
                }
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
                        model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product.gambar[page]}" else "",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(30.sdp)
                    )
                    AsyncImage(
                        model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product.gambar[page]}" else "",
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
                            model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product.gambar[iteration]}" else "",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(30.sdp)
                        )
                        AsyncImage(
                            model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product.gambar[iteration]}" else "",
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
                Text(
                    text = product?.tipe?.capitalizeEachWord() ?: "",
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
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(bottom = 4.sdp, top = 8.sdp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 24.sdp, bottom = 4.sdp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Text(text = "Stock    : ", style = MaterialTheme.typography.titleSmall)
                    Text(
                        text = "${product?.jumlahStok ?: 0} ${product?.unit ?: ""}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Text(text = "Berat     : ", style = MaterialTheme.typography.titleSmall)
                    Text(
                        text = "${product?.berat ?: 0} gram",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Text(
                text = product?.deskripsi ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MyStyle.colors.textGrey,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.sdp)
            )

            Text(
                text = "Data Penjual",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(bottom = 4.sdp, top = 8.sdp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.sdp)
                    .padding(bottom = 32.sdp)
            ) {
                Text(
                    text = product?.dataPemilik?.fullname ?: "",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = product?.dataPemilik?.nomorTelepon ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = product?.dataPemilik?.profile?.alamat ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "${product?.dataPemilik?.profile?.kotaKabupaten ?: ""}, " +
                            "${product?.dataPemilik?.profile?.kecamatan ?: ""}, " +
                            "${product?.dataPemilik?.profile?.kelurahan ?: ""}, " +
                            (product?.dataPemilik?.profile?.kodePos ?: ""),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}