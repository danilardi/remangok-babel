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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
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
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.components.common.LoadingDialog
import com.ipb.remangokbabel.ui.components.common.SelectableDialog
import com.ipb.remangokbabel.ui.components.product.AddImageLayout
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.ProductViewModel
import com.ipb.remangokbabel.utils.navigateToAndMakeTop
import com.ipb.remangokbabel.utils.navigateToBack
import com.ipb.remangokbabel.utils.reduceFileImage
import com.ipb.remangokbabel.utils.uriToFile
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AddProductScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    product: ProductItem? = null,
) {
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    var isEdit by remember { mutableStateOf(false) }

    var productImagesUri by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var productName by remember { mutableStateOf("") }
    var productWeight by remember { mutableIntStateOf(0) }
    var productTipe by remember { mutableStateOf("kepiting") } // 'kepiting', 'olahan', 'benih', 'pakan', 'cangkang', 'capit', 'sisa produksi'
    var productPrice by remember { mutableIntStateOf(0) }
    var productDescription by remember { mutableStateOf("") }
    var productStock by remember { mutableIntStateOf(0) }
    var productUnit by remember { mutableStateOf("kg") } // 'kg', 'pcs'
    var productImages by remember { mutableStateOf<List<Uri>>(emptyList()) }

    var showDialogTipe by remember { mutableStateOf(false) }
    var showDialogUnit by remember { mutableStateOf(false) }

    var deletedImageIndex by remember { mutableIntStateOf(0) }

    val productTipeItems = listOf(
        "kepiting", "olahan", "benih", "pakan", "cangkang", "capit", "sisa produksi"
    )
    val productUnitItems = listOf("kg", "pcs")

    LaunchedEffect(Unit) {
        if (product != null) {
            isEdit = true
            productName = product.nama
            productWeight = product.berat
            productTipe = product.tipe
            productPrice = product.hargaSatuan
            productDescription = product.deskripsi
            productStock = product.jumlahStok
            productUnit = product.unit
            productImages = product.gambar.map { it.toUri() }
            productImagesUri = productImages
        }
    }

    LaunchedEffect(productImagesUri, productImages) {
        if (productImagesUri.isNotEmpty() && productImagesUri.size > productImages.size) {
            val imageFile =
                uriToFile(productImagesUri[productImages.size], context).reduceFileImage()
            viewModel.uploadImage(imageFile)
        }
    }

    viewModel.uploadImageState.collectAsState().value.let {
        if (it != null) {
            productImages = productImages.toMutableList().apply {
                add(it.data.filename.toUri())
            }.distinct()
            viewModel.clearUploadImageState()
        }
    }

    viewModel.deleteImageState.collectAsState().value.let {
        if (it != null) {
            productImagesUri = productImagesUri.filterIndexed { i, _ -> i != deletedImageIndex }
            productImages = productImages.filterIndexed { i, _ -> i != deletedImageIndex }
            viewModel.clearDeleteImageState()
        }
    }

    viewModel.uploadProductState.collectAsState().value.let {
        if (it != null) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            viewModel.clearUploadProductState()
            navigateToBack(navController)
        }
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
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp, bottom = 48.sdp),
            enabled = productImages.isNotEmpty() && productName.isNotEmpty() && productDescription.isNotEmpty() && productTipe.isNotEmpty() && productWeight > 0 && productPrice > 0 && productStock > 0,
            onClick = {
                val request = UploadProductRequest(
                    jumlahStok = productStock,
                    nama = productName,
                    deskripsi = productDescription,
                    tipe = productTipe,
                    hargaSatuan = productPrice,
                    berat = productWeight,
                    gambar = productImages.map { it.toString() }
                )
                if (isEdit) {
                    viewModel.updateProduct(product?.id ?: -1, request)
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
                Row(
                    Modifier
                        .padding(start = 8.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Tipe",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.sdp)
                    )
                    Text(
                        text = productTipe,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        showDialogTipe = true
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint = MyStyle.colors.primaryMain,
                            modifier = Modifier.size(16.sdp)
                        )
                    }
                    SelectableDialog(showDialog = showDialogTipe,
                        selectableItems = productTipeItems,
                        selectedItem = productTipe,
                        onDismissRequest = { showDialogTipe = false },
                        onSelectedItemChange = {
                            productTipe = it
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
                            .weight(1f)
                    ) {
                        productWeight = it.toInt()
                    }
                    Text(
                        text = "g", style = MaterialTheme.typography.bodyMedium, modifier = Modifier
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
                        text = productUnit,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                    )
                }
                Row(
                    Modifier
                        .padding(start = 8.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Unit",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.sdp)
                    )
                    Text(
                        text = productUnit,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        showDialogUnit = true
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint = MyStyle.colors.primaryMain,
                            modifier = Modifier.size(16.sdp)
                        )
                    }
                    SelectableDialog(showDialog = showDialogUnit,
                        selectableItems = productUnitItems,
                        selectedItem = productUnit,
                        onDismissRequest = { showDialogUnit = false },
                        onSelectedItemChange = {
                            productUnit = it
                        })
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
