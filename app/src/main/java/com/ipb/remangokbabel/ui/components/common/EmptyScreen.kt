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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.model.component.ScreenType
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    title: String = "Data Kosong",
    desc: String = "",
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
            textAlign = TextAlign.Center,
            fontSize = 18.ssp,
            color = MyStyle.colors.textBlack,
            fontWeight = FontWeight(700),
            modifier = Modifier.padding(top = 26.sdp)
        )
        if(desc.isNotEmpty()) {
            Text(
                text = desc,
                textAlign = TextAlign.Center,
                fontSize = 14.ssp,
                color = MyStyle.colors.textGrey,
                fontWeight = FontWeight(400),
                modifier = Modifier.padding(top = 5.sdp)
            )
        }
    }
}