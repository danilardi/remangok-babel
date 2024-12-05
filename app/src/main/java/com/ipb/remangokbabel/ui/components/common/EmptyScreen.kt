package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.model.component.ScreenType
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    title: String = "Data Kosong",
    type: ScreenType = ScreenType.Empty,
) {
    var contentDescription by remember { mutableStateOf("") }
    var painter by remember { mutableStateOf(R.drawable.empty) }

    LaunchedEffect(type) {
        when (type) {
            ScreenType.Unauthorized -> {
                contentDescription = "Unauthorized"
                painter = R.drawable.empty
            }

            ScreenType.Error -> {
                contentDescription = "Error"
                painter = R.drawable.empty
            }

            else -> {
                contentDescription = "Empty Data"
                painter = R.drawable.empty
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MyStyle.colors.bgWhite)
            .padding(16.sdp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = painter),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}