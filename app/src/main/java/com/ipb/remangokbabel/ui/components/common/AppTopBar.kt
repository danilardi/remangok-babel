package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String = "Manajemen Produk",
    backButton: Boolean = false,
    isProfile: Boolean = true,
    isManagementProduct: Boolean = true,
    onClickToProfile: () -> Unit = {},
    onClickToManagementProduct: () -> Unit = {},
    onClickBackButton: () -> Unit = {}
) {
    val context = LocalContext.current
    val paperPrefs = PaperPrefs(context)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MyStyle.colors.primaryMain)
            .padding(horizontal = 16.sdp)
    ) {
        if (backButton && paperPrefs.getRole() != "admin")
            IconButton(
                onClick = { onClickBackButton() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(16.sdp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    tint = MyStyle.colors.textWhite
                )
            }
        Text(
            text = title,
            fontWeight = FontWeight.W700,
            fontSize = 14.ssp,
            color = MyStyle.colors.textWhite,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .align(if (backButton) Alignment.Center else Alignment.CenterStart)
                .padding(vertical = 12.sdp),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            if (isManagementProduct)
                IconButton(
                    onClick = onClickToManagementProduct,
                    modifier = Modifier.size(30.sdp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Manajemen Produk",
                        tint = MyStyle.colors.bgWhite,
                        modifier = Modifier.size(20.sdp)
                    )
                }
            if (isProfile)
                IconButton(
                    onClick = onClickToProfile,
                    modifier = Modifier
                        .size(30.sdp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profil",
                        tint = MyStyle.colors.bgWhite,
                        modifier = Modifier.size(20.sdp)
                    )
                }
        }
    }
}