package com.ipb.remangokbabel.ui.screen.product

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.UploadProductRequest
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.product.AddImageLayout
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.navigateToBack
import com.ipb.remangokbabel.utils.reduceFileImage
import com.ipb.remangokbabel.utils.uriToFile
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun AddProductScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    productId: Int = -1,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var isEdit by remember { mutableStateOf(false) }

    var productImagesUri by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var productName by remember { mutableStateOf("") }
    var productWeight by remember { mutableIntStateOf(0) }
    var productFase by remember { mutableStateOf("dewasa") }
    var productPrice by remember { mutableIntStateOf(0) }
    var productDescription by remember { mutableStateOf("") }
    var productStock by remember { mutableIntStateOf(0) }
    var productImages by remember { mutableStateOf<List<Uri>>(emptyList()) }

    var showDialogFase by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(false) }

    var deletedImageIndex by remember { mutableIntStateOf(0) }

    val productFaseItems = listOf("telur", "dewasa")

    LaunchedEffect(Unit) {
        if (productId != -1) {
            isEdit = true
            viewModel.getProduct(productId)
        }

        coroutineScope.launch {
            viewModel.getProductResponse.collect { it ->
                val product = it.detailProductData.detailProduk
                productName = product.nama
                productWeight = product.berat
                productFase = "dewasa"
                productPrice = product.hargaSatuan
                productDescription = product.deskripsi
                productStock = product.jumlahStok
                productImages = product.gambar.map { it.toUri() }
                productImagesUri = productImages
            }

        }

        coroutineScope.launch {
            viewModel.uploadImageResponse.collect { uploadImageResponse ->
                productImages = productImages.toMutableList().apply {
                    add(uploadImageResponse.data.filename.toUri())
                }.distinct()
                if (productImages.size == productImagesUri.size) {
                    viewModel.showLoading.emit(false)
                }
            }
        }
        coroutineScope.launch {
            viewModel.deleteImageResponse.collect {
                productImagesUri = productImagesUri.filterIndexed { i, _ -> i != deletedImageIndex }
                productImages = productImages.filterIndexed { i, _ -> i != deletedImageIndex }
            }
        }
        coroutineScope.launch {
            viewModel.uploadProductResponse.collect {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                navigateToBack(navController)
            }
        }
        coroutineScope.launch {
            viewModel.showLoading.collect {
                showLoading = it
            }
        }
        coroutineScope.launch {
            viewModel.errorResponse.collect { errorResponse ->
                viewModel.showLoading.emit(false)
                Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
                if (errorResponse.message == "token anda tidak valid") {
                    paperPrefs.deleteAllData()
                    navigateToAndMakeTop(navController, Screen.Login.route)
                }
            }
        }
    }

    LaunchedEffect(productImagesUri) {
        if (productImagesUri.isNotEmpty() && productImagesUri.size > productImages.size) {
            viewModel.showLoading.emit(true)
            productImagesUri.forEachIndexed { index, image ->
                if (index >= productImages.size) {
                    val imageFile = uriToFile(image, context).reduceFileImage()
                    viewModel.uploadImage(imageFile)
                }
            }
        }
    }

    if (showLoading) {
        LoadingDialog()
    }

    Scaffold(topBar = {
        BackTopBar(title = if (isEdit) "Edit Produk" else "Tambah Produk") {
            navController.popBackStack()
        }
    }, bottomBar = {
        ButtonCustom(
            text = "Simpan",
            modifier = Modifier
                .shadow(
                    elevation = 10.sdp, // Adjust the elevation as needed
                    shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                    clip = false // Don't clip the content to the shape
                )
                .background(color = MyStyle.colors.bgWhite)
                .padding(16.sdp),
            enabled = productImages.isNotEmpty() && productName.isNotEmpty() && productDescription.isNotEmpty() && productFase.isNotEmpty() && productWeight > 0 && productPrice > 0 && productStock > 0,
            onClick = {
                val request = UploadProductRequest(
                    jumlahStok = productStock,
                    nama = productName,
                    deskripsi = productDescription,
                    faseHidup = productFase,
                    hargaSatuan = productPrice,
                    berat = productWeight,
                    gambar = productImages.map { it.toString() }
                )
                if (isEdit) {
                    viewModel.updateProduct(productId, request)
                } else {
                    viewModel.uploadProduct(request)
                }
            },
        )
    }) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .fillMaxSize()
                .background(MyStyle.colors.bgSecondary)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 4.sdp)
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 8.sdp)
                    .padding(bottom = 8.sdp)
            ) {
                Text(
                    text = "Foto Produk",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.sdp)
                )

                AddImageLayout(
                    productImages,
                    onImageDeleted = { index ->
                        deletedImageIndex = index
                        viewModel.deleteImage(productImages[index].lastPathSegment!!)
                    },
                ) { images ->
                    productImagesUri = productImagesUri.toMutableList().apply {
                        addAll(images)
                    }.distinct()
                }

            }
            Column(
                modifier = Modifier
                    .padding(top = 4.sdp)
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 8.sdp)
                    .padding(bottom = 8.sdp)
            ) {
                Text(
                    text = "Nama Produk",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.sdp)
                )
                InputLayout(
                    value = productName,
                    hint = "Masukkan Nama Produk",
                    border = false,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.sdp)
                ) {
                    productName = it
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 4.sdp)
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
                    .padding(horizontal = 8.sdp)
                    .padding(bottom = 8.sdp)
            ) {
                Text(
                    text = "Deskripsi Produk",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.sdp)
                )
                InputLayout(
                    value = productDescription,
                    hint = "Masukkan Deskripsi Produk",
                    border = false,
                    isLongInput = true,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.sdp)
                ) {
                    productDescription = it
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 4.sdp)
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgWhite)
            ) {
//                Row(
//                    Modifier
//                        .padding(start = 8.sdp),
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
//                    Text(
//                        text = "Fase",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.padding(start = 8.sdp)
//                    )
//                    Text(
//                        text = productFase,
//                        style = MaterialTheme.typography.bodyMedium,
//                        textAlign = TextAlign.End,
//                        modifier = Modifier.weight(1f)
//                    )
//                    IconButton(onClick = {
//                        showDialogFase = true
//                    }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
//                            contentDescription = null,
//                            tint = MyStyle.colors.primaryMain,
//                            modifier = Modifier.size(16.sdp)
//                        )
//                    }
//                    SelectableDialog(showDialog = showDialogFase,
//                        selectableItems = productFaseItems,
//                        selectedItem = productFase,
//                        onDismissRequest = { showDialogFase = false },
//                        onSelectedItemChange = {
//                            productFase = it
//                        })
//                }
//                HorizontalDivider(
//                    color = MyStyle.colors.bgSecondary,
//                )
                Row(
                    modifier = Modifier.padding(horizontal = 8.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Berat",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.sdp)
                    )
                    InputLayout(
                        value = productWeight.toString(),
                        hint = "Atur",
                        border = false,
                        isRow = true,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        isNumber = true,
                        modifier = Modifier
                            .padding(horizontal = 8.sdp)
                            .align(Alignment.CenterVertically)
                            .weight(1f)
                    ) {
                        productWeight = it.toInt()
                    }
                    Text(
                        text = "g",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                    )
                }
                HorizontalDivider(
                    color = MyStyle.colors.bgSecondary,
                )
                Row(
                    modifier = Modifier.padding(horizontal = 8.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Harga",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.sdp)
                    )
                    InputLayout(
                        value = productPrice.toString(),
                        hint = "Atur",
                        border = false,
                        isRow = true,
                        isNumber = true,
                        prefix = "Rp",
                        textStyle = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(horizontal = 8.sdp)
                            .align(Alignment.CenterVertically)
                            .fillMaxSize()
                    ) {
                        productPrice = it.toInt()
                    }
                }
                HorizontalDivider(
                    color = MyStyle.colors.bgSecondary,
                )
                Row(
                    modifier = Modifier.padding(horizontal = 8.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Stok",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.sdp)
                    )
                    InputLayout(
                        value = productStock.toString(),
                        hint = "Atur",
                        isNumber = true,
                        border = false,
                        isRow = true,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(horizontal = 8.sdp)
                            .align(Alignment.CenterVertically)
                            .weight(1f)
                    ) {
                        productStock = it.toInt()
                    }
                    Text(
                        text = "KG",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun AddProductScreenPreview() {
    val navController: NavHostController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        AddProductScreen(
            navController = navController,
        )
    }

}
