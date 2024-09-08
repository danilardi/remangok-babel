package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.ipb.remangokbabel.ui.components.home.Search
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    search: Boolean = false,
    onSearch: Boolean = false,
    onSearchChange: (Boolean) -> Unit = {},
) {
    if (onSearch && search) {
        Search(
            modifier = modifier
                .padding(vertical = 8.sdp, horizontal = 16.sdp)
        )
    } else {
        Box(
            modifier = modifier
                .background(MyStyle.colors.bgWhite)
                .padding(vertical = 8.sdp, horizontal = 16.sdp)
                .height(SearchBarDefaults.InputFieldHeight)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MyStyle.typography.baseBold,
                fontSize = 20.ssp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
            if (search) {
            IconButton(
                onClick = {
                    onSearchChange(false)
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
                    .background(MyStyle.colors.bgSecondary)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }}
        }
    }
}