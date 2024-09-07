package com.ipb.remangokbabel.ui.components.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    maxSelectionCount: Int = 10,
//    imageSelected: List<Uri> = emptyList(),
    onImageSelected: (List<Uri>) -> Unit = {}
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                onImageSelected(listOf(uri))
            }
        }
    )

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = if (maxSelectionCount > 1) {
            maxSelectionCount
        } else {
            2
        }),
        onResult = { uris -> onImageSelected(uris) }
    )

    fun launchPhotoPicker() {
        if (maxSelectionCount > 1) {
            multiplePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.sdp))
            .dashedBorder(
                width = 1.sdp,
                on = 6.sdp,
                off = 6.sdp,
                shape = RoundedCornerShape(4.sdp),
                brush = SolidColor(MyStyle.colors.textPrimary),
            )
            .clickable { launchPhotoPicker() },
       contentAlignment = Alignment.Center
    ) {
            Text(
                text = "+ Tambah Foto",
                style = MaterialTheme.typography.bodyMedium,
                color = MyStyle.colors.textPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.sdp)
            )
    }
}

