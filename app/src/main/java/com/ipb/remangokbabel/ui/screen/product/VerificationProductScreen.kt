package com.ipb.remangokbabel.ui.screen.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.components.common.BackTopBar
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.utils.capitalizeEachWord
import com.ipb.remangokbabel.utils.navigateToBack
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun VerificationProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    product: ProductItem? = null,
) {

    Scaffold(
        topBar = {
            BackTopBar(
                title = "Buat Pesanan Baru",
                onClickBackButton = { navigateToBack(navController) })
        },
        bottomBar = {
            ButtonCustom(
                text = "Buat Pesanan",
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
                text = "Detail Produk",
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
                Column(
                    modifier = Modifier
                        .padding(top = 16.sdp, end = 16.sdp, bottom = 16.sdp)
                        .fillMaxWidth()
                        .height(120.sdp),
                ) {
                    Text(
                        text = product?.nama?.capitalizeEachWord() ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = product?.tipe ?: "",
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
                }
            }
        }
    }
}