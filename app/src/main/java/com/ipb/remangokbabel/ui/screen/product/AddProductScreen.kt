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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.model.request.UploadProductRequest
import com.ipb.remangokbabel.ui.components.common.AppTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.common.SelectableDialog
import com.ipb.remangokbabel.ui.components.product.AddImageLayout
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.navigateTo
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
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
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var productImagesUri by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var productName by remember { mutableStateOf("") }
    var productWeight by remember { mutableIntStateOf(0) }
    var productFase by remember { mutableStateOf("") }
    var productPrice by remember { mutableIntStateOf(0) }
    var productDescription by remember { mutableStateOf("") }
    var productStock by remember { mutableIntStateOf(0) }
    var productImages by remember { mutableStateOf<List<Uri>>(emptyList()) }

    var showDialogFase by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(false) }

    var deletedImageIndex by remember { mutableIntStateOf(0) }

    val productFaseItems = listOf("telur", "dewasa")

    LaunchedEffect(Unit) {
        if (BuildConfig.DEBUG) {
            productName = "Kepiting"
            productWeight = 100
            productFase = "telur"
            productPrice = 10000
            productDescription = "Product Description"
            productStock = 10
        }
        coroutineScope.launch {
            viewModel.uploadImageResponse.collect { uploadImageResponse ->
                productImages = productImages.toMutableList().apply {
                    add(uploadImageResponse.data.filename.toUri())
                }.distinct()
                if (productImages.size == productImagesUri.size) {
                    viewModel.showLoading(false)
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
                navigateTo(navController, Screen.ManagementStock.route)
            }
        }
        coroutineScope.launch {
            viewModel.showLoading.collect {
                showLoading = it
            }
        }
        coroutineScope.launch {
            viewModel.errorResponse.collect { errorResponse ->
                viewModel.showLoading(false)
                Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
                if (errorResponse.message == "token anda tidak valid") {
                    paperPrefs.deleteAllData()
                    navigateToAndMakeTop(navController, Screen.Auth.route)
                }
            }
        }
    }

    LaunchedEffect(productImagesUri) {
        if (productImagesUri.isNotEmpty()) {
            viewModel.showLoading(true)
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
        AppTopBar(title = "Tambah Produk") {
            navController.popBackStack()
        }
    }, bottomBar = {
        ButtonCustom(
            text = "Simpan",
            modifier = Modifier.padding(8.sdp),
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
                viewModel.uploadProduct(request)
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
                Row(
                    Modifier
                        .padding(start = 8.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Fase",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.sdp)
                    )
                    Text(
                        text = productFase,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        showDialogFase = true
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint = MyStyle.colors.primaryMain,
                            modifier = Modifier.size(16.sdp)
                        )
                    }
                    SelectableDialog(showDialog = showDialogFase,
                        selectableItems = productFaseItems,
                        selectedItem = productFase,
                        onDismissRequest = { showDialogFase = false },
                        onSelectedItemChange = {
                            productFase = it
                        })
                }
                HorizontalDivider(
                    color = MyStyle.colors.bgSecondary,
                )
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
                            .fillMaxSize()
                    ) {
                        productWeight = it.toInt()
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
                        modifier = Modifier.padding(horizontal = 8.sdp)
                    ) {
                        productStock = it.toInt()
                    }
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
