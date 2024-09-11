package com.ipb.remangokbabel.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.model.response.ProdukItem
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ProductCard(
    product: ProdukItem,
    modifier: Modifier = Modifier,
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
                model = if (product.gambar.isNotEmpty()) "${BuildConfig.BASE_URL}${product.gambar.first()}" else "",
                contentDescription = product.nama,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.sdp)
                    .clip(RoundedCornerShape(8.sdp))
            )
//            Image(
//                painter = rememberAsyncImagePainter(product.gambar.firstOrNull() ?: ""),
////                painter = painterResource(R.drawable.kepiting),
//                contentDescription = product.nama,
////                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.sdp)
//                    .clip(RoundedCornerShape(8.sdp))
//            )
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.sdp))

            Text(
                text = product.nama,
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
                    text = product.hargaSatuan.toRupiah(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )
                val fase = if (product.faseHidup == "dewasa") "Kepiting" else "Benih"
                Text(
                    text = fase,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(4.sdp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Rating Indicator
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
////                    Icon(
////                        imageVector = Icons.Default.Star,
////                        contentDescription = null,
////                        tint = MyStyle.colors.textKuning,
////                        modifier = Modifier.size(14.sdp)
////                    )
////                    Text(
////                        text = "4.5",
////                        style = MaterialTheme.typography.bodySmall,
////                        color = MyStyle.colors.textPrimary
////                    )
//
//                }

                Text(
                    text = "Grade: ",
                    style = MaterialTheme . typography . bodySmall,
                    color = MyStyle.colors.textGrey
                )
                val grade =
                    if (product.berat >= 500) "A"
                    else if (product.berat >= 200) "B"
                    else "C"
                Text(
                    text = grade,
                    style = MaterialTheme . typography . bodySmall,
                    color = MyStyle.colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )

                // Stock Indicator
                Text(
                    text = "Stock: ${product.jumlahStok}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(8.sdp))

//            // Action Buttons
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                ButtonCustom(
//                    text = "Add to Cart",
//                    shape = RoundedCornerShape(16.sdp),
//                ) {
//
//                }
//            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun ProductCardPreview() {
    val productItems = listOf(
        ProdukItem(
            jumlahStok = 10,
            nama = "Product Name",
            hargaSatuan = 10000,
            updatedAt = "2021-08-01",
            idOwner = "1",
            berat = 100,
            faseHidup = "Dewasa",
            createdAt = "2021-08-01",
            id = 1,
            deskripsi = "Product Description",
            gambar = listOf("https://picsum.photos/200/300")
        ),
        ProdukItem(
            jumlahStok = 10,
            nama = "Product Name",
            hargaSatuan = 10000,
            updatedAt = "2021-08-01",
            idOwner = "1",
            berat = 100,
            faseHidup = "Dewasa",
            createdAt = "2021-08-01",
            id = 2,
            deskripsi = "Product Description",
            gambar = listOf("https://picsum.photos/200/300")
        )
    )
    RemangokBabelTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.sdp),
                horizontalArrangement = Arrangement.spacedBy(16.sdp),
                verticalArrangement = Arrangement.spacedBy(16.sdp),
                modifier = Modifier
            ) {
                items(productItems, key = { it.id }) { product ->
                    ProductCard(product = product)
                }
            }
        }
    }
}
