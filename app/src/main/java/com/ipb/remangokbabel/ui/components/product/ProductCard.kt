package com.ipb.remangokbabel.ui.components.product

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.utils.capitalizeEachWord
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: ProductItem? = null,
    onViewDetailsClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 4.sdp,
            topEnd = 4.sdp,
            bottomStart = 4.sdp,
            bottomEnd = 4.sdp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MyStyle.colors.bgWhite,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.sdp
        ),
        onClick = onViewDetailsClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row {
                AsyncImage(
                    model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product.gambar.first()}" else "",
                    contentScale = ContentScale.Crop,
                    contentDescription = product?.nama?.capitalizeEachWord() ?: "Nama Produk",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(93.sdp)
                        .clip(RoundedCornerShape(6.sdp))
                )
            }

            Column(
                modifier = modifier.padding(vertical = 8.sdp, horizontal = 6.sdp)
            ) {
                Text(
                    text = product?.nama?.capitalizeEachWord() ?: "testing",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = MyStyle.colors.textBlack,
                        fontSize = 10.ssp,
                        fontWeight = FontWeight.W500,
                    )
                )
                Text(
                    text = "${product?.berat ?: 0} g | ${product?.tipe?.capitalizeEachWord() ?: ""}",
                    color = MyStyle.colors.text600,
                    fontSize = 8.ssp,
                    fontWeight = FontWeight.W400,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 2.sdp)
                )
                Text(
                    text = product?.hargaSatuan?.toRupiah() ?: 0.toRupiah(),
                    style = TextStyle(
                        color = MyStyle.colors.textBlack,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight(700)
                    ),
                    modifier = Modifier.padding(top = 6.sdp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.sdp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MyStyle.colors.primaryMain,
                        modifier = modifier.size(8.sdp)
                    )
                    Text(
                        text = "Kecamatan ${product?.dataPemilik?.profile?.kecamatan ?: ""}",
                        style = TextStyle(
                            color = MyStyle.colors.textBlack,
                            fontSize = 8.ssp,
                            fontWeight = FontWeight(600)
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

            }
        }
    }
}