package com.ipb.remangokbabel.ui.screen.basic

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.ui.theme.MyStyle
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MyStyle.colors.bgSplash),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_remangok2),
            contentScale = ContentScale.Crop,
            contentDescription = "Splash Screen",
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "Remangok Babel",
            style = MyStyle.typography.baseBold,
            fontSize = 36.ssp,
            color = MyStyle.colors.textGrey,
            modifier = Modifier
                .padding(top = 24.sdp),
        )
    }
}