package com.ipb.remangokbabel.ui.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ir.kaaveh.sdpcompose.ssp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Remangok Babel",
            fontFamily = FontFamily.Monospace,
            fontSize = 28.ssp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Hey!",
            fontFamily = FontFamily.Default,
            fontSize = 28.ssp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "Welcome Back",
            fontFamily = FontFamily.Default,
            fontSize = 24.ssp,
            fontWeight = FontWeight.Medium,
        )

    }
}