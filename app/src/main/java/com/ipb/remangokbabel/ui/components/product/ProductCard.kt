package com.ipb.remangokbabel.ui.components.product

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: ProductItem? = null,
    onViewDetailsClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 12.dp),
        shape = RoundedCornerShape(12.sdp),
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
                .padding(8.sdp)
        ) {
            Row (
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Text("Penjual : ${product?.dataPemilik?.fullname ?: ""}",
                    fontSize = 12.sp,
                    style = MyStyle.typography.baseMedium,
                )
                CustomTextContainer(text = "Diterima", color = MyStyle.colors.primaryMain, modifier = modifier )
            }
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

            Spacer(modifier = Modifier.height(4.sdp))
            Text(
                text = product?.nama ?: "",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = product?.hargaSatuan?.toRupiah() ?: 0.toRupiah(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = product?.tipe?.capitalizeEachWord() ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(4.sdp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.sdp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.sdp)
                )
                Text(
                    text = product?.dataPemilik?.fullname ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 4.sdp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.sdp)
            ) {
                Text(
                    text = "Berat: ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MyStyle.colors.textGrey
                )
                Text(
                    text = "${product?.berat ?: 0} g",
                    style = MaterialTheme.typography.bodySmall,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )

            }

            // Stock Indicator
            Text(
                text = "Stock: ${product?.jumlahStok ?: 0} ${product?.unit ?: ""}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier
                        .padding(top = 4.sdp)
            )
            Text(
                text = "Kecamatan ${product?.dataPemilik?.profile?.kecamatan ?: ""}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 4.sdp),
            )
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

