package com.ipb.remangokbabel.ui.components.product

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.theme.MyColors
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
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
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
                    contentDescription = product?.nama ?: "Nama Produk",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(93.dp)
                        .clip(RoundedCornerShape(6.sdp))
                )
            }

            Column(
                modifier = modifier.padding(vertical = 8.dp, horizontal = 6.dp)
            ) {
                Text(
                    text = product?.nama?.capitalizeEachWord() ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = MyStyle.colors.textBlack,
                        fontSize = 10.ssp,
                        fontWeight = FontWeight.W500,
                    ),

                )
                Row(
                    modifier = modifier.padding(top = 2.dp)
                ) {
                    Text(
                        text = "${product?.berat ?: 0} g | ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    )
                    Text(
                        text = product?.tipe?.capitalizeEachWord() ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.height(6.sdp))
                Text(
                    text = product?.hargaSatuan?.toRupiah() ?: 0.toRupiah(),
                    style = TextStyle(
                        color = MyStyle.colors.textBlack,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight(weight = 700)
                    ),
                )

                Spacer(modifier = Modifier.height(8.sdp))

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ){

                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location", tint = MyStyle.colors.primaryMain, modifier = modifier.width(12.dp).height(12.dp))
                    Text(
                        text = "Kecamatan ${product?.dataPemilik?.profile?.kecamatan ?: ""}",
                        style = TextStyle(
                            color = MyStyle.colors.textBlack,
                            fontSize = 8.ssp,
                            fontWeight = FontWeight(600)
                        ),
                        overflow = TextOverflow.Ellipsis,
                    )
                }

            }
        }
    }
}


@Composable
fun CustomTextContainer(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 10.sp,
            style = MyStyle.typography.baseMedium,
            color = color,
            modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp)
        )
    }
}

