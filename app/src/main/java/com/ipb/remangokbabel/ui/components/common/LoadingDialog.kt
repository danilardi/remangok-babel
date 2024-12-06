package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.spr.jetpack_loading.components.indicators.BallSpinFadeLoaderIndicator

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment= Center,
            modifier = modifier
                .size(100.dp)
                .background(MyStyle.colors.bgWhite, shape = RoundedCornerShape(8.dp))
        ) {
            BallSpinFadeLoaderIndicator(
                color = MyStyle.colors.bgBlack
            )
        }
    }
}