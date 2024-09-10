package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ButtonCustom(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(8.sdp),
    type: ButtonType = ButtonType.Primary,
    onClick: () -> Unit
) {
    var containerColor by remember { mutableStateOf(Color.Unspecified) }
    var borderColor by remember { mutableStateOf(Color.Unspecified) }
    var textColor by remember { mutableStateOf(Color.Unspecified) }
    var thisType by remember { mutableStateOf(type) }

    thisType = if (!enabled) ButtonType.Disabled else type

    when (thisType) {
        ButtonType.Primary -> {
            containerColor = MyStyle.colors.primaryMain
            borderColor = MyStyle.colors.primaryBorder
            textColor = MyStyle.colors.textWhite
        }
        ButtonType.Disabled -> {
            containerColor = MyStyle.colors.disableSurface
            borderColor = MyStyle.colors.disableBorder
            textColor = MyStyle.colors.disableBorder
        }
        ButtonType.Outline -> {
            containerColor = MyStyle.colors.bgWhite
            borderColor = MyStyle.colors.primaryMain
            textColor = MyStyle.colors.primaryMain
        }
        ButtonType.Danger -> {
            containerColor = MyStyle.colors.errorMain
            borderColor = MyStyle.colors.errorBorder
            textColor = MyStyle.colors.textWhite
        }
    }
    Button(
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        border = BorderStroke(1.sdp, borderColor),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = textColor, disabledContainerColor = containerColor, disabledContentColor = textColor),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 34.sdp),
    ) {
        Text(text, style = MyStyle.typography.xsMedium, color = textColor, textAlign = TextAlign.Center, modifier = Modifier.padding(0.sdp))
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    RemangokBabelTheme {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.sdp)
        ) {
            ButtonCustom(
                text = "Tessadf ijasdfkl klasjkdj fkjasdfasdf asdf asdf sdf asdfkalsj ds",
                modifier = Modifier.weight(1f)
            ) {

            }
            ButtonCustom(
                text = "Tessadf ijasdfkl klasjkdj fkjkalsj ds",
                modifier = Modifier.weight(1f)
            ) {

            }
        }
    }
}