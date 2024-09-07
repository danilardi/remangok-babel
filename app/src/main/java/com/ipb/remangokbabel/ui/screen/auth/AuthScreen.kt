package com.ipb.remangokbabel.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ipb.remangokbabel.ViewModelFactory
import com.ipb.remangokbabel.di.Injection
import com.ipb.remangokbabel.ui.components.auth.LoginForm
import com.ipb.remangokbabel.ui.components.auth.RegisterForm
import com.ipb.remangokbabel.ui.navigation.Screen
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.viewmodel.AuthViewModel
import com.ipb.remangokbabel.utils.navigateTo
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AuthScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MyStyle.colors.bgPrimary)
    systemUiController.setNavigationBarColor(color = MyStyle.colors.bgSecondary)

    var pageActive by remember { mutableIntStateOf(0) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MyStyle.colors.bgPrimary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Remangok Babel",
                style = MyStyle.typography.baseBold,
                fontSize = 28.ssp,
                color = MyStyle.colors.textWhite,
                modifier = Modifier
                    .padding(top = 24.sdp)
                    .align(Alignment.CenterHorizontally),
            )
            Column(
                modifier = Modifier
                    .padding(start = 24.sdp, top = 16.sdp)
            ) {
                Text(
                    text = "Hai!,",
                    style = MyStyle.typography.baseMedium,
                    fontSize = 24.ssp,
                    color = MyStyle.colors.textWhite,
                )
                Text(
                    text = "Selamat datang🦀",
                    style = MyStyle.typography.baseMedium,
                    fontSize = 16.ssp,
                    color = MyStyle.colors.textWhite,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 32.sdp, topEnd = 32.sdp))
                .background(color = MyStyle.colors.bgSecondary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.sdp)
                    .padding(horizontal = 16.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectorPageAuth(
                    text = "Masuk",
                    isActive = pageActive == 0,
                    onClick = { pageActive = 0 },
                    modifier = Modifier.weight(1f)
                )
                SelectorPageAuth(
                    text = "Daftar",
                    isActive = pageActive == 1,
                    onClick = { pageActive = 1 },
                    modifier = Modifier.weight(1f)
                )
            }
            if (pageActive == 0) {
                LoginForm(modifier = modifier, viewModel = viewModel) {
                   navigateTo(navController, Screen.Home.route)
                }
            } else {
                RegisterForm(modifier = modifier, viewModel = viewModel) {
                    pageActive = 0
                }
            }
        }
    }
}

@Composable
fun SelectorPageAuth(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.sdp)
            .clip(RoundedCornerShape(8.sdp))
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = text,
            style = MyStyle.typography.lgBold,
            color = MyStyle.colors.textBlack,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 4.sdp),
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 2.sdp)
                .clip(RoundedCornerShape(8.sdp))
                .alpha(if (isActive) 1f else 0f),
            thickness = 4.sdp,
            color = MyStyle.colors.bgPrimary,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun AuthScreenPreview() {
    val navController: NavHostController = rememberNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AuthScreen(
            navController = navController,
        )
    }

}