package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    icon: Boolean = false,
    dropShadow: Boolean = true,
    onClickIcon: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = if (dropShadow) 4.sdp else 0.sdp, // Adjust the elevation as needed
                shape = RectangleShape, // Ensure shadow is drawn for the entire Box
                clip = false // Don't clip the content to the shape
            )
            .background(MyStyle.colors.bgWhite)
            .padding(vertical = 8.sdp)
            .padding(start = 16.sdp , end = 8.sdp)
            .height(SearchBarDefaults.InputFieldHeight)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MyStyle.typography.lgBold,
            fontSize = 20.ssp,
            color = MyStyle.colors.textPrimary,
            modifier = Modifier
                .align(Alignment.Center),
        )
        if (icon) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(32.sdp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
                    .background(MyStyle.colors.bgWhite)
            )
        }
    }
}