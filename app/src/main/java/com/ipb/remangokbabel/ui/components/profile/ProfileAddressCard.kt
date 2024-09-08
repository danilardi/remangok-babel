package com.ipb.remangokbabel.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ipb.remangokbabel.model.response.ProfilesItem
import com.ipb.remangokbabel.ui.theme.MyStyle
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ProfileAddressCard(
    item: ProfilesItem,
    onClickDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MyStyle.colors.bgWhite)
            .padding(horizontal = 16.sdp, vertical = 16.sdp)
            .clickable {
                onClickDetail()
            }
    ) {
        Row {
            Text(
                text = "${item.namaDepan} ${item.namaBelakang}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "  |  ${item.nomorTelepon}",
                style = MaterialTheme.typography.bodyMedium,
                color = MyStyle.colors.textGrey
            )
        }
        Text(
            text = item.alamat,
            style = MaterialTheme.typography.bodySmall,
            color = MyStyle.colors.textGrey
        )
        Text(
            text = "${item.namaKelurahan}, ${item.namaKecamatan}, ${item.namaKotaKabupaten}, ${item.namaProvinsi}, ${item.kodePos}",
            style = MaterialTheme.typography.bodySmall,
            color = MyStyle.colors.textGrey
        )
    }
    HorizontalDivider(
        thickness = 1.sdp,
        color = MyStyle.colors.bgSecondary
    )
}