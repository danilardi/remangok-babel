package com.ipb.remangokbabel.ui.screen.basic

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ipb.remangokbabel.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay


@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onSplashFinished: () -> Unit = {}
) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(500)
        )
        delay(1000)
        onSplashFinished()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFEDDE))
    ) {
            Image(
                painter = painterResource(id = R.drawable.remangok_logo),
                contentDescription = "remangok logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)
            )
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(vertical = 24.sdp),
            ) {
                Text(
                    text = "Dikembangkan Oleh :",
                    fontSize = 12.ssp,
                    fontWeight = FontWeight(600),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_supportby),
                    contentDescription = "remangok logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
    }
}
