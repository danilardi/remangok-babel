package com.ipb.remangokbabel.ui.components.product

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.model.response.ProductItem
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.utils.capitalizeEachWord
import com.ipb.remangokbabel.utils.toRupiah
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

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
        shape = RoundedCornerShape(6.sdp),
        colors = CardDefaults.cardColors(
            containerColor = MyStyle.colors.bgWhite,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.sdp,
        ),
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.sdp, vertical = 12.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.sdp)
                )
                Text(
                    text = product?.dataPemilik?.fullname ?: "",
                    fontSize = 12.ssp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.sdp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                val status = when (product?.status) {
                    "accepted" -> "Diterima"
                    "rejected" -> "Ditolak"
                    else -> "Menunggu Verifikasi"
                }
                val statusColor = when (product?.status) {
                    "accepted" -> MyStyle.colors.successMain
                    "rejected" -> MyStyle.colors.errorMain
                    else -> MyStyle.colors.warningMain
                }
                Text(
                    text = status,
                    fontSize = 10.ssp,
                    fontWeight = FontWeight(600),
                    color = statusColor,
                    modifier = Modifier
                        .padding(horizontal = 8.sdp, vertical = 4.sdp)
                )
            }
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 10f), 0f)
            Canvas(
                Modifier
                    .padding(horizontal = 10.sdp)
                    .fillMaxWidth()
                    .height(1.dp)) {
                drawLine(
                    color = MyStyle.colors.disableBorder,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    pathEffect = pathEffect
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.sdp, vertical = 12.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = "${BuildConfig.BASE_URL}${product?.gambar?.first()}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.sdp)
                        .clip(RoundedCornerShape(8.sdp))
                        .border(1.sdp, MyStyle.colors.disableBorder, RoundedCornerShape(8.sdp))
                )
                Column(
                    modifier = Modifier
                        .padding(start = 12.sdp)
                        .fillMaxWidth()
                        .height(90.sdp),
                ) {
                    Text(
                        text = product?.nama?.capitalizeEachWord() ?: "",
                        fontSize = 10.ssp,
                        fontWeight = FontWeight(500),
                        color = MyStyle.colors.textBlack,
                    )
                    Text(
                        text = "${product?.berat ?: 0} g | ${product?.tipe ?: ""} | Stok : ${product?.jumlahStok ?: ""} g",
                        fontSize = 8.ssp,
                        fontWeight = FontWeight(400),
                        color = MyStyle.colors.neutral600,
                        modifier = Modifier.padding(top = 4.sdp)
                    )
                    Text(
                        text = (product?.hargaSatuan ?: 0).toRupiah(),
                        fontSize = 12.ssp,
                        fontWeight = FontWeight(700),
                        modifier = Modifier.padding(top = 8.sdp)
                    )
                    Text(
                        text = product?.deskripsi ?: "",
                        fontSize = 8.ssp,
                        fontWeight = FontWeight(400),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 8.sdp)
                    )
                }
            }
//            HorizontalDivider()
//            Row {
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 16.sdp, vertical = 8.sdp)
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(text = "Berat : ", style = MaterialTheme.typography.titleSmall)
//                        Text(
//                            text = "${product?.berat ?: 0} g",
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(text = "Tipe : ", style = MaterialTheme.typography.titleSmall)
//                        Text(text = product?.tipe ?: "", style = MaterialTheme.typography.bodySmall)
//                    }
//                }
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 16.sdp, vertical = 8.sdp)
//
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(text = "Stok : ", style = MaterialTheme.typography.titleSmall)
//                        Text(
//                            text = "${product?.jumlahStok ?: ""} ${product?.unit ?: ""}",
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                }
//            }
//            HorizontalDivider()
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.sdp)
                    .padding(bottom = 12.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.sdp)
            ) {
                if (paperPrefs.getRole() == "admin") {
                    if (product?.status == "requested") {
                        ButtonCustom(
                            text = "Verifikasi Product",
                            type = ButtonType.Success,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            onClickCardItem()
                        }
                    } else if (product?.status == "rejected") {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "Alasan Penolakan :",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = product.alasanPenolakan ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2
                            )
                        }
                    }

                } else {
                    if (product?.status == "requested") {
                        ButtonCustom(
                            text = "Batalkan Request",
                            type = ButtonType.Danger,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            onDeleteClick()
                        }
                        ButtonCustom(
                            text = "Edit",
                            modifier = Modifier
                                .weight(1f),
                            type = ButtonType.Success
                        ) {
                            onEditClick()
                        }
                    } else if (product?.status == "accepted") {
                        ButtonCustom(
                            text = "Hapus",
                            type = ButtonType.Danger,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            onDeleteClick()
                        }
                        ButtonCustom(
                            text = "Edit",
                            modifier = Modifier
                                .weight(1f),
                            type = ButtonType.Success
                        ) {
                            onEditClick()
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "Alasan Penolakan :",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = product?.alasanPenolakan ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2
                            )
                        }
                        ButtonCustom(
                            text = "Perbaiki",
                            type = ButtonType.Warning,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            onEditClick()
                        }
                    }
                }
            }
        }
    }
}