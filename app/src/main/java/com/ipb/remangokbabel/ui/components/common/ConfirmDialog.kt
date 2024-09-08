package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true)
@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MyStyle.colors.bgSecondary, shape = RoundedCornerShape(8.sdp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.question),
                contentDescription = "Warning",
                modifier = Modifier
                    .padding(16.sdp)
                    .size(48.sdp)
            )
            Text(
                text = "Apakah Anda yakin?",
                style = MaterialTheme.typography.titleLarge,
                color = MyStyle.colors.textBlack,
                modifier = Modifier.padding( start = 16.sdp, end = 16.sdp)
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.sdp, vertical = 16.sdp),
            ) {
                ButtonCustom(
                    text = "Batal",
                    type = ButtonType.Outline,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.sdp)
                ){}
                ButtonCustom(
                    text = "Ya",
                    type = ButtonType.Primary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.sdp)
                ){}
            }
        }
    }
}