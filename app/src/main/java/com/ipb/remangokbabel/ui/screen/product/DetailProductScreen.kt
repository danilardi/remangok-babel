package com.ipb.remangokbabel.ui.screen.product

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.model.request.VerifyProductRequest
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.navigateToBack
import com.ipb.remangokbabel.utils.openWhatsApp
import com.ipb.remangokbabel.utils.toRupiah2
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

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
    var profileData by remember { mutableStateOf(null as ProfilesItem?) }

    var alasanPenolakan by remember { mutableStateOf("") }
    var showDialogReject by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (paperPrefs.getProfile() != null)
            profileData = paperPrefs.getProfile()
    }

    viewModel.verifyProductState.collectAsState().value?.let {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        viewModel.clearVerifyProductState()
        navigateToBack(navController)
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

    if (showDialogReject) {
        Dialog(onDismissRequest = { showDialogReject = false }) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgSecondary, shape = RoundedCornerShape(8.sdp))
                    .padding(16.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Alasan Penolakan",
                    style = MaterialTheme.typography.titleLarge,
                )
                InputLayout(
                    value = alasanPenolakan,
                    hint = "Masukkan alasan penolakan produk",
                    border = true,
                    isLongInput = true,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.sdp)
                ) {
                    alasanPenolakan = it
                }
                Row(
                    modifier = Modifier.padding(top = 8.sdp),
                ) {
                    ButtonCustom(
                        text = "Batal",
                        type = ButtonType.Outline,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.sdp)
                    ) {
                        showDialogReject = false
                    }
                    ButtonCustom(
                        text = "Ya",
                        enabled = alasanPenolakan.isNotEmpty(),
                        type = ButtonType.Primary,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.sdp)
                    ) {
                        val data = VerifyProductRequest(
                            idProduk = "${product?.id}",
                            status = "rejected",
                            alasanPenolakan = alasanPenolakan
                        )
                        viewModel.verifyProduct(data)
                        showDialogReject = false
                    }
                }
            }
        }
    }

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
                if (profileData?.dataDiri?.id != product?.dataPemilik?.id) {
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
                        val message =
                            "Halo, saya ${profileData?.dataDiri?.fullname} ingin memesan ${product?.nama}"
                        openWhatsApp(
                            context = context,
                            phoneNumber = product?.dataPemilik?.nomorTelepon ?: "",
                            message = message
                        )
                    }
                }
            } else if (paperPrefs.getRole() == "admin") {
                if (product?.status == "requested") {
                    Row(
                        Modifier
                            .shadow(
                                elevation = 10.sdp, // Adjust the elevation as needed
                                shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                                clip = false // Don't clip the content to the shape
                            )
                            .background(color = MyStyle.colors.bgWhite)
                            .padding(16.sdp)
                    ) {
                        ButtonCustom(
                            text = "Terima",
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.sdp)
                        ) {
                            val data = VerifyProductRequest(
                                idProduk = "${product.id}",
                                status = "accepted",
                            )
                            viewModel.verifyProduct(data)
                        }
                        ButtonCustom(
                            text = "Tolak",
                            type = ButtonType.Danger,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.sdp)
                        ) {
                            showDialogReject = true
                        }
                    }
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .background(MyStyle.colors.bgWhite)
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
//            LazyRow(
//                Modifier
//                    .padding(top = 8.sdp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.Start
//            ) {
//                items(pagerState.pageCount) { iteration ->
//                    Box(modifier = Modifier
//                        .padding(horizontal = 4.sdp)
//                        .padding(
//                            start = if (iteration == 0) 8.sdp else 0.sdp,
//                            end = if (iteration == pagerState.pageCount - 1) 8.sdp else 0.sdp
//                        )
//                        .size(48.sdp)
//                        .clickable {
//                            coroutineScope.launch {
//                                pagerState.animateScrollToPage(iteration)
//                            }
//                        }
//                    ) {
//                        AsyncImage(
//                            model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product.gambar[iteration]}" else "",
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .blur(30.sdp)
//                        )
//                        AsyncImage(
//                            model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product.gambar[iteration]}" else "",
//                            contentDescription = null,
//                            modifier = Modifier
//                                .fillMaxSize()
//                        )
//                    }
//                }
//            }
            Column(
                modifier = Modifier.padding(16.sdp)
            ) {
                Text(
                    text = product?.nama ?: "Testing",
                    fontSize = 14.ssp,
                    fontWeight = FontWeight(500),
                    color = MyStyle.colors.textBlack,
                )
                Text(
                    text = "Rp ${product?.hargaSatuan?.toRupiah2() ?: 0.toRupiah2()}",
                    fontSize = 16.ssp,
                    fontWeight = FontWeight(700),
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier.padding(top = 4.sdp)
                )
                Text(
                    text = "Detail Produk",
                    textDecoration = TextDecoration.Underline,
                    fontSize = 12.ssp,
                    fontWeight = FontWeight(600),
                    color = MyStyle.colors.textBlack,
                    modifier = Modifier
                        .padding(top = 12.sdp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.sdp),
                    modifier = Modifier.padding(top = 6.sdp)
                ) {
                    Text(
                        text = "Berat   : ${product?.berat ?: 0}g",
                        fontWeight = FontWeight(400),
                        fontSize = 12.ssp,
                        color = MyStyle.colors.textBlack
                    )
                    Text(
                        text = "Tipe     : ${product?.tipe ?: ""}",
                        fontWeight = FontWeight(400),
                        fontSize = 12.ssp,
                        color = MyStyle.colors.textBlack
                    )
                    Text(
                        text = "Stok     : ${product?.jumlahStok ?: 0} gram",
                        fontWeight = FontWeight(400),
                        fontSize = 12.ssp,
                        color = MyStyle.colors.textBlack
                    )
                }
                Text(
                    text = product?.deskripsi ?: "",
                    fontWeight = FontWeight(400),
                    fontSize = 12.ssp,
                    color = MyStyle.colors.textBlack,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = "Data Penjual",
                    textDecoration = TextDecoration.Underline,
                    fontSize = 12.ssp,
                    fontWeight = FontWeight(600),
                    color = MyStyle.colors.textBlack,
                    modifier = Modifier
                        .padding(top = 12.sdp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.sdp),
                    modifier = Modifier.padding(top = 6.sdp)
                ) {
                    Text(
                        text = product?.dataPemilik?.fullname ?: "",
                        fontWeight = FontWeight(400),
                        fontSize = 12.ssp,
                        color = MyStyle.colors.textBlack
                    )
                    Text(
                        text = product?.dataPemilik?.nomorTelepon ?: "",
                        fontWeight = FontWeight(400),
                        fontSize = 12.ssp,
                        color = MyStyle.colors.textBlack
                    )
                    Text(
                        text = "${product?.dataPemilik?.profile?.kotaKabupaten ?: ""}, " +
                                "${product?.dataPemilik?.profile?.kecamatan ?: ""}, " +
                                "${product?.dataPemilik?.profile?.kelurahan ?: ""}, " +
                                (product?.dataPemilik?.profile?.kodePos ?: ""),
                        fontWeight = FontWeight(400),
                        fontSize = 12.ssp,
                        color = MyStyle.colors.textBlack
                    )
                }
            }
        }
    }
}