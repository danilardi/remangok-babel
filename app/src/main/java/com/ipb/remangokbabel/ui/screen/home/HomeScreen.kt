package com.ipb.remangokbabel.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Box(modifier = Modifier) {
            Text(
                text = "Remangok Babel",
                style = MyStyle.typography.baseBold,
                fontSize = 28.ssp,
                color = MyStyle.colors.textBlack,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 48.sdp)
            )
        }
    }
}