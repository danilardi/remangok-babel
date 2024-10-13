package com.ipb.remangokbabel.ui.components.product

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.model.response.ProdukItem
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ProductManagementCard(
    product: ProdukItem,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MyStyle.colors.bgWhite,
        ),
        shape = RoundedCornerShape(0.sdp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            Row {
                AsyncImage(
                    model = "${BuildConfig.BASE_URL}${product.gambar.first()}",
                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
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
                        text = product.nama,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = product.hargaSatuan.toRupiah(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.sdp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = product.deskripsi,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = 8.sdp, end = 8.sdp)
                    )
                }
            }
            HorizontalDivider()
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.sdp, vertical = 8.sdp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val grade =
                            if (product.berat >= 500) "A"
                            else if (product.berat >= 200) "B"
                            else "C"
                        Text(text = "Grade : ", style = MaterialTheme.typography.titleSmall)
                        Text(text = grade, style = MaterialTheme.typography.bodySmall)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Fase Hidup : ", style = MaterialTheme.typography.titleSmall)
                        Text(text = product.faseHidup, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.sdp, vertical = 8.sdp)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Stok : ", style = MaterialTheme.typography.titleSmall)
                        Text(text = product.jumlahStok.toString(), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            HorizontalDivider()
            Row {
                ButtonCustom(
                    text = "Edit",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.sdp, vertical = 8.sdp)
                ) {
                    onEditClick()
                }
                ButtonCustom(
                    text = "Hapus",
                    type = ButtonType.Danger,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.sdp, vertical = 8.sdp)
                ) {
                    onDeleteClick()
                }
            }
        }
    }
}