package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.model.component.ScreenType
import ir.kaaveh.sdpcompose.sdp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun EmptyScreen(
    title: String = "Data Kosong",
    type: ScreenType = ScreenType.Empty,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.sdp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (type) {
            ScreenType.Empty -> {
                Image(
                    painter = painterResource(id = R.drawable.empty),
                    contentDescription = "Empty Data",
                    contentScale = ContentScale.Fit
                )
                Text(text = title, style = MaterialTheme.typography.titleLarge)
            }
            ScreenType.Unauthorized -> {
                Image(
                    painter = painterResource(id = R.drawable.empty),
                    contentDescription = "Unauthorized",
                    contentScale = ContentScale.Fit
                )
                Text(text = title, style = MaterialTheme.typography.titleLarge)
            }
            ScreenType.Error -> {
                Image(
                    painter = painterResource(id = R.drawable.empty),
                    contentDescription = "Error",
                    contentScale = ContentScale.Fit
                )
                Text(text = title, style = MaterialTheme.typography.titleLarge)
            }

            else -> {}
        }
    }

}