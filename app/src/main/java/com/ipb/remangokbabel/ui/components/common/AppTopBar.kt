package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onClickBackButton: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(8.sdp)
    ) {
        IconButton(
            onClick = { onClickBackButton() }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = MyStyle.colors.primaryMain)
        }
        Text(text = title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(start = 8.sdp))
    }
}