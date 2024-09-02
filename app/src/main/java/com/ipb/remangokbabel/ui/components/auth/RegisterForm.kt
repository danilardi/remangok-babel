package com.ipb.remangokbabel.ui.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import ir.kaaveh.sdpcompose.sdp


@Composable
fun RegisterForm(
    modifier: Modifier = Modifier
) {
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier) {
        InputLayout(
            title = "Nama Lengkap",
            value = fullname,
            hint = "Silahkan masukkan nama anda",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            fullname = it
        }
        InputLayout(
            title = "Email",
            value = email,
            hint = "Silahkan masukkan email anda",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            email = it
        }
        InputLayout(
            title = "Kata Sandi",
            value = password,
            hint = "Silahkan masukkan kata sandi anda",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 16.sdp)
        ) {
            password = it
        }

        ButtonCustom(
            text = "Daftar",
            modifier = Modifier
                .padding(horizontal = 16.sdp)
                .padding(top = 24.sdp)
        ) {

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.sdp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Sudah punya akun?")
            Text(
                text = "Masuk",
                modifier = Modifier
                    .padding(start = 4.sdp),
                color = MyStyle.colors.textHijau,
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun RegisterFormPreview() {
    RemangokBabelTheme {
        RegisterForm()
    }
}