package com.ipb.remangokbabel.ui.screen.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.utils.navigateTo
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Column(
        modifier
            .padding(horizontal = 16.sdp, vertical = 24.sdp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(horizontal = 16.sdp)
                .weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.remangok_logo_without_text),
                contentDescription = "onboarding image",
                contentScale = ContentScale.FillHeight,
                modifier = modifier
                    .fillMaxWidth()
                    .height(268.sdp)
                    .padding(bottom = 21.sdp)
            )
            Text(
                "Temukan Semua Olahan\nKepiting Disini",
                textAlign = TextAlign.Center,
                fontSize = 18.ssp,
                fontWeight = FontWeight.W700,
                color = MyStyle.colors.textBlack,
                modifier = modifier.padding(bottom = 12.sdp)
            )
            Text(
                "This document communicates the brand identity of brand name. Clearly articulating the mission, values and persona for the design of all subsequent brand artifacts.",
                textAlign = TextAlign.Center,
                fontSize = 12.ssp,
                fontWeight = FontWeight.W400,
                color = MyStyle.colors.textBlack,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(top = 16.sdp)
        ) {
            ButtonCustom(
                text = "Daftar",
                modifier = modifier.padding(bottom = 16.sdp)
            ) {
                navigateTo(navController, Screen.Register.route)
            }
            Row(
                modifier = Modifier.padding(bottom = 25.sdp)
            ) {
                Text(
                    "Sudah Punya Akun?",
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.W400,
                    color = MyStyle.colors.textBlack,
                )
                Text(
                    "Masuk",
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.W700,
                    color = MyStyle.colors.primaryMain,
                    modifier = modifier
                        .padding(start = 6.sdp)
                        .clickable {
                            navigateTo(navController, Screen.Login.route)
                        }
                )
            }
        }
    }

}