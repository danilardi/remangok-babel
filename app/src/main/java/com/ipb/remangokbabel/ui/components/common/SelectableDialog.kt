package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.model.component.ButtonType
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableDialog(
    showDialog: Boolean,
    onSelectedItemChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    selectedItem: String = "",
    selectableItems: List<String> = listOf(),
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf(selectedItem) }

    if (showDialog) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
            ) {
                LazyColumn(
                    modifier = Modifier,
                    userScrollEnabled = true
                ) {
                    items(selectableItems) { item ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    selectedItem = item
                                }
                                .padding(8.sdp)
                                .fillMaxWidth()
                                .height(24.sdp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = item, modifier = Modifier.weight(1f))
                            if (item == selectedItem) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MyStyle.colors.primaryMain,
                                    modifier = Modifier.size(24.sdp)
                                )
                            }
                        }
                        if (item != selectableItems.last()) {
                            HorizontalDivider(
                                color = MyStyle.colors.bgSecondary,
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.sdp)
                        .padding(top = 8.sdp, bottom = 16.sdp),
                ) {
                    ButtonCustom(
                        text = "Pilih",
                        modifier = Modifier.weight(1f)
                    ) {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onSelectedItemChange(selectedItem)
                                onDismissRequest()
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(8.sdp))
                    ButtonCustom(
                        text = "Batal",
                        type = ButtonType.Outline,
                        modifier = Modifier.weight(1f)
                    ) {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissRequest()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun SelectableDialogPreview() {
    var showDialog by remember { mutableStateOf(false) }
    var selectableItems by remember { mutableStateOf(listOf("Item 1", "Item 2", "Item 3")) }
    RemangokBabelTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                ButtonCustom(onClick = { showDialog = true }, text = "text")
                SelectableDialog(
                    showDialog = showDialog,
                    selectedItem = "Item 1",
                    selectableItems = selectableItems,
                    onDismissRequest = {
                        showDialog = false
                    },
                    onSelectedItemChange = {}
                )
            }
        }
    }
}
