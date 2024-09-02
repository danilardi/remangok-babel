package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ButtonCustom(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.sdp),
        border = BorderStroke(1.sdp, MyStyle.colors.primaryBorder),
        colors = ButtonDefaults.buttonColors(containerColor = MyStyle.colors.primaryMain),
        modifier = modifier
            .fillMaxWidth()
            .height(34.sdp),
    ) {
        Text(text, style = MyStyle.typography.xsMedium, color = MyStyle.colors.textWhite)
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    RemangokBabelTheme {
        ButtonCustom(
            text = "Tess"
        ) {

        }
    }
}