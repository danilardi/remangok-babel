package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp

@Composable
fun BackTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onClickBackButton: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.sdp, // Adjust the elevation as needed
                shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                clip = false // Don't clip the content to the shape
            )
            .background(color = MyStyle.colors.bgWhite)
            .padding(8.sdp)
    ) {
        IconButton(
            onClick = { onClickBackButton() }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = MyStyle.colors.primaryMain)
        }
        Text(text = title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(start = 8.sdp))
    }
}