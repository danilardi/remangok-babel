package com.ipb.remangokbabel.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.R
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ProfileScreen(
    name: String,
    province: String,
    city: String,
    address: String,
    postalCode: String,
    phoneNumber: String,
) {
    val profileData = remember {
        Profile(
            name = name,
            province = province,
            city = city,
            address = address,
            postalCode = postalCode,
            phoneNumber = phoneNumber
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.sdp)
                .fillMaxSize()
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.sdp))

            ProfileImage(
                Modifier
                    .size(100.sdp)
                    .clip(RoundedCornerShape(8.sdp))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.sdp))

            ProfileItem(label = "Nama", value = profileData.name)
            ProfileItem(label = "Provinsi", value = profileData.province)
            ProfileItem(label = "Kabupaten/Kota", value = profileData.city)
            ProfileItem(label = "Alamat", value = profileData.address)
            ProfileItem(label = "Kode Pos", value = profileData.postalCode)
            ProfileItem(label = "Nomor Telepon", value = profileData.phoneNumber)

            Spacer(modifier = Modifier.height(16.sdp))

            Row {
                ButtonCustom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = "Edit Profil",
                    onClick = { /* TODO: Implement edit profile */ }
                )

                Spacer(modifier = Modifier.width(8.sdp))

                ButtonCustom(
                    type = ButtonType.Danger,
                    modifier = Modifier
                        .weight(1f),
                    text = "Hapus Profil",
                    onClick = { /* TODO: Implement delete profile */ }
                )

            }

            Spacer(modifier = Modifier.height(16.sdp))

            //Button Logout//
//            Button(
//                onClick = {},
//                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(34.sdp)
//                    .clip(RoundedCornerShape(8.sdp))
//            ) { Text("Logout") }
        }
    }
}

@Composable
fun ProfileImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.avatar_example),
        contentDescription = "Profile Image",
        modifier = modifier
    )
}

data class Profile(
    val name: String,
    val province: String,
    val city: String,
    val address: String,
    val postalCode: String,
    val phoneNumber: String,
)

@Preview(showBackground = true)
@Composable
fun Profile2Preview() {
    RemangokBabelTheme {
        ProfileScreen(
            name = "Mas Zikri",
            province = "Jawa Timur",
            city = "Surabaya",
            address = "Jl. Raya No. 456",
            postalCode = "40234",
            phoneNumber = "+62 812 3456 7890"
        )
    }
}