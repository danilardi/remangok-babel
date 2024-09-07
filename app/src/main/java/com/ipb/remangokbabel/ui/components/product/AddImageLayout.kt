package com.ipb.remangokbabel.ui.components.product

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.ipb.remangokbabel.BuildConfig
import com.ipb.remangokbabel.ui.components.common.ImagePicker
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp


@Composable
fun AddImageLayout(
    selectedImages: List<Uri>,
    onImageDeleted: (Int) -> Unit = {},
    onImageSelected: (List<Uri>) -> Unit = {},
) {
    val itemCount = selectedImages.size
    val columns = 4

    Column(
        modifier = Modifier.padding(horizontal = 6.sdp)
    ) {
        var rows = (itemCount / columns)
        if (itemCount.mod(columns) >= 0) {
            rows += 1
        }

        for (rowId in 0 until rows) {
            val firstIndex = rowId * columns

            Row {
                for (columnId in 0 until columns) {
                    val index = firstIndex + columnId
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        if (index < itemCount) {
                            AsyncImage(
                                model = "${BuildConfig.BASE_URL}${selectedImages[index]}",
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .padding(2.sdp)
                                    .fillMaxSize()
                                    .border(
                                        1.sdp,
                                        MyStyle.colors.disableBorder,
                                        RoundedCornerShape(4.sdp)
                                    )
                            )
                            IconButton(
                                onClick = { onImageDeleted(index) },
                                modifier = Modifier
                                    .size(16.sdp)
                                    .clip(CircleShape)
                                    .align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = MyStyle.colors.bgWhite,
                                    modifier = Modifier
                                        .size(16.sdp)
                                        .background(MyStyle.colors.errorMain),
                                )
                            }
                        } else if (index == itemCount) {
                            ImagePicker(
                                modifier = Modifier
                                    .padding(2.sdp)
                                    .fillMaxSize(),
                                maxSelectionCount = 10,
                                onImageSelected = onImageSelected
                            )
                        }
                    }
                }
            }
        }
    }
}