package com.ipb.remangokbabel.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ProductManagementCard(
    modifier: Modifier = Modifier,
    product: ProductItem? = null,
    onClickCardItem: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    Card(
        onClick = {
            onClickCardItem()
        },
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
            if (paperPrefs.getRole() == "admin") {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.sdp)
                    )
                    Text(
                        text = product?.dataPemilik?.fullname ?: "",
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.sdp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.sdp)
                            .clip(RoundedCornerShape(16.sdp))
                            .background(
                                color = when (product?.status) {
                                    "accepted" -> MyStyle.colors.successMain
                                    "requested" -> MyStyle.colors.warningMain
                                    else -> MyStyle.colors.errorMain
                                }
                            )
                    ) {
                        val status = when (product?.status) {
                            "accepted" -> "Diterima"
                            "rejected" -> "Ditolak"
                            else -> "Menunggu Verifikasi"
                        }
                        Text(
                            text = status,
                            style = MaterialTheme.typography.bodySmall,
                            color = MyStyle.colors.textWhite,
                            modifier = Modifier
                                .padding(horizontal = 8.sdp, vertical = 4.sdp)
                                .align(Alignment.Center)
                        )
                    }
                }
                HorizontalDivider()
            }
            Row {
                AsyncImage(
                    model = "${BuildConfig.BASE_URL}${product?.gambar?.first()}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
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
                        text = product?.nama ?: "",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = (product?.hargaSatuan ?: 0).toRupiah(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.sdp)
                    )
                    if (paperPrefs.getRole() == "user") Box(
                        modifier = Modifier
                            .padding(vertical = 8.sdp)
                            .clip(RoundedCornerShape(16.sdp))
                            .background(
                                color = when (product?.status) {
                                    "accepted" -> MyStyle.colors.successMain
                                    "requested" -> MyStyle.colors.warningMain
                                    else -> MyStyle.colors.errorMain
                                }
                            )
                    ) {
                        val status = when (product?.status) {
                            "accepted" -> "Diterima"
                            "rejected" -> "Ditolak"
                            else -> "Menunggu Verifikasi"
                        }
                        Text(
                            text = status,
                            style = MaterialTheme.typography.bodySmall,
                            color = MyStyle.colors.textWhite,
                            modifier = Modifier
                                .padding(horizontal = 8.sdp, vertical = 4.sdp)
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = product?.deskripsi ?: "",
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
                        Text(text = "Berat : ", style = MaterialTheme.typography.titleSmall)
                        Text(text = "${product?.berat ?: 0} g", style = MaterialTheme.typography.bodySmall)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Tipe : ", style = MaterialTheme.typography.titleSmall)
                        Text(text = product?.tipe ?: "", style = MaterialTheme.typography.bodySmall)
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
                        Text(
                            text = "${product?.jumlahStok ?: ""} ${product?.unit ?: ""}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (paperPrefs.getRole() == "admin") {
                    if (product?.status == "requested") {
                        ButtonCustom(
                            text = "Verifikasi Product",
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.sdp, vertical = 8.sdp)
                        ) {
                            onClickCardItem()
                        }
                    } else if (product?.status == "rejected") {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.sdp, vertical = 8.sdp)
                        ) {
                            Text(text = "Alasan Penolakan :", style = MaterialTheme.typography.titleSmall)
                            Text(text = product.alasanPenolakan ?: "", style = MaterialTheme.typography.bodySmall, overflow = TextOverflow.Ellipsis, maxLines = 2)
                        }
                    }

                } else {
                    if (product?.status == "requested") {
                        ButtonCustom(
                            text = "Edit",
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.sdp, vertical = 8.sdp)
                        ) {
                            onEditClick()
                        }
                        ButtonCustom(
                            text = "Batalkan Request",
                            type = ButtonType.Danger,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.sdp, vertical = 8.sdp)
                        ) {
                            onDeleteClick()
                        }
                    } else if (product?.status == "accepted") {
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
                    } else {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.sdp, vertical = 8.sdp)
                        ) {
                            Text(text = "Alasan Penolakan :", style = MaterialTheme.typography.titleSmall)
                            Text(text = product?.alasanPenolakan ?: "", style = MaterialTheme.typography.bodySmall, overflow = TextOverflow.Ellipsis, maxLines = 2)
                        }
                        ButtonCustom(
                            text = "Perbaiki",
                            type = ButtonType.Warning,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.sdp, vertical = 8.sdp)
                        ) {
                            onEditClick()
                        }
                    }
                }
            }
        }
    }
}