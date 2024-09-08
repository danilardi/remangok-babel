package com.ipb.remangokbabel.ui.screen.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ipb.remangokbabel.ui.components.common.ButtonCustom
import com.ipb.remangokbabel.ui.components.common.InputLayout
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    name: String,
    province: String,
    city: String,
    address: String,
    postalCode: String,
    phoneNumber: String,
    onSaveClick: (Profile) -> Unit,
    onCancel: () -> Unit
) {
    var newName by remember { mutableStateOf(name) }
    var newProvince by remember { mutableStateOf(province) }
    var newCity by remember { mutableStateOf(city) }
    var newAddress by remember { mutableStateOf(address) }
    var newPostalCode by remember { mutableStateOf(postalCode) }
    var newPhoneNumber by remember { mutableStateOf(phoneNumber) }

    var showDialog by remember { mutableStateOf(false) }
    var backPressCount by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    BackHandler {
        backPressCount++
        if (backPressCount == 1) {
            scope.launch {
                delay(2000) // Reset backPressCount after 2 seconds
                backPressCount = 0
            }
        } else {
            showDialog = true
        }
    }

    if (showDialog) {
        ConfirmCancelDialog(
            onConfirm = {
                showDialog = false
                onCancel()
            },
            onDismiss = {
                showDialog = false
                backPressCount = 0
            }
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
                text = "Edit Profile",
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

            InputLayout(
                title = "Nama",
                value = newName,
                hint = "Masukkan Nama",
                onValueChange = { newName = it }
            )

            Spacer(modifier = Modifier.height(16.sdp))

            InputLayout(
                title = "Provinsi",
                value = newProvince,
                hint = "Masukkan Provinsi",
                onValueChange = { newProvince = it }
            )

            Spacer(modifier = Modifier.height(16.sdp))

            InputLayout(
                title = "Kabupaten/Kota",
                value = newCity,
                hint = "Masukkan Kabupaten/Kota",
                onValueChange = { newCity = it }
            )

            Spacer(modifier = Modifier.height(16.sdp))

            InputLayout(
                title = "Alamat",
                value = newAddress,
                hint = "Masukkan Alamat",
                onValueChange = { newAddress = it }
            )

            Spacer(modifier = Modifier.height(16.sdp))

            InputLayout(
                title = "Kode Pos",
                value = newPostalCode,
                hint = "Masukkan Kode Pos",
                onValueChange = { newPostalCode = it }
            )

            Spacer(modifier = Modifier.height(16.sdp))

            InputLayout(
                title = "Nomor Telepon",
                value = newPhoneNumber,
                hint = "Masukkan Nomor Telepon",
                isNumber = true,
                onValueChange = { newPhoneNumber = it }
            )

            /* Edit Profile yang Lama*/
//            ProfileEditItem(label = "Nama", value = newName, onValueChange = { newName = it })
//            ProfileEditItem(label = "Provinsi", value = newProvince, onValueChange = { newProvince = it })
//            ProfileEditItem(label = "Kabupaten/Kota", value = newCity, onValueChange = { newCity = it })
//            ProfileEditItem(label = "Alamat", value = newAddress, onValueChange = { newAddress = it })
//            ProfileEditItem(label = "Kode Pos", value = newPostalCode, onValueChange = { newPostalCode = it })
//            ProfileEditItem(label = "Nomor Telepon", value = newPhoneNumber, onValueChange = { newPhoneNumber = it })

            Spacer(modifier = Modifier.height(16.sdp))

            ButtonCustom(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.sdp)
                    .clip(RoundedCornerShape(8.sdp)),
                text = "Simpan",
                onClick = {
                    onSaveClick(
                        Profile(
                            name = newName,
                            province = newProvince,
                            city = newCity,
                            address = newAddress,
                            postalCode = newPostalCode,
                            phoneNumber = newPhoneNumber
                        )
                    )
                }
            )
        }
    }
}

/* Old Input Edit Profile */
@Composable
fun ProfileEditItem(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 8.sdp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.sdp)),
            singleLine = true
        )
    }
}

@Composable
fun ConfirmCancelDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Surface(
            shape = RoundedCornerShape(8.sdp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.sdp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Batalkan perubahan?")
                Spacer(modifier = Modifier.height(16.sdp))
                Row {
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Ya")
                    }
                    Spacer(modifier = Modifier.width(8.sdp))
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Tidak")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    RemangokBabelTheme {
        EditProfileScreen(
            name = "Mas Zikri",
            province = "Jawa Timur",
            city = "Surabaya",
            address = "Jl. Raya No. 456",
            postalCode = "40234",
            phoneNumber = "+62 812 3456 7890",
            onSaveClick = {},
            onCancel = {}
        )
    }
}
