package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.ui.theme.MyStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(
    items: List<String>,
    modifier: Modifier = Modifier,
    title: String = "",
    hint: String = "",
    selectedText: String = "",
    isEnable: Boolean = true,
    onSelectItem: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
//                if (isEnable)
                    expanded = !expanded
            }
        ) {
            InputLayout(
                title = title,
                hint = hint,
                value = selectedText,
                isEnable = false,
                isExpanse = expanded,
                isDropdown = true,
                borderColor = if (isEnable) MyStyle.colors.bgPrimary else MyStyle.colors.disableBorder,
            )

            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                enabled = isEnable,
                modifier = if (isEnable) Modifier.fillMaxWidth().menuAnchor().alpha(0f) else Modifier.alpha(0f)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyStyle.colors.bgSecondary)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onSelectItem(item)
                            expanded = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MyStyle.colors.bgSecondary)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ExposedDropdownMenuBoxPreview(modifier: Modifier = Modifier) {
    ExposedDropdownMenuBox(listOf("Pembeli", "Penjual"), title = "tes", hint = "cekk")
}