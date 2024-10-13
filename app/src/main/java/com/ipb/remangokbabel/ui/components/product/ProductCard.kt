package com.ipb.remangokbabel.ui.components.product

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.model.response.ProdukItem
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: ProdukItem? = null,
    onViewDetailsClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(160.sdp)
            .padding(8.sdp),
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
            AsyncImage(
                model = if (product?.gambar?.isNotEmpty() == true) "${BuildConfig.BASE_URL}${product.gambar.first()}" else "",
                contentScale = ContentScale.Crop,
                contentDescription = product?.nama ?: "Nama Produk",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.sdp)
                    .clip(RoundedCornerShape(8.sdp))
            )
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.sdp))

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
                val fase = if (product?.faseHidup == "dewasa") "Kepiting" else "Kepiting"
                Text(
                    text = fase,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
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
                    text = product?.owner?.fullname ?: "nama gua",
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
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 4.sdp)
            ) {
                Text(
                    text = "Grade: ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MyStyle.colors.textGrey
                )
                val grade =
                    if ((product?.berat ?: 0) >= 500) "A"
                    else if ((product?.berat ?: 0) >= 200) "B"
                    else "C"
                Text(
                    text = grade,
                    style = MaterialTheme.typography.bodySmall,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )

            }

            // Stock Indicator
            Text(
                text = "Stock: ${product?.jumlahStok ?: 0} kg",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier
                        .padding(top = 4.sdp)
            )
            Text(
                text = product?.owner?.profiles?.get(0)?.namaKotaKabupaten ?: "Kabupaten blabla",
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


