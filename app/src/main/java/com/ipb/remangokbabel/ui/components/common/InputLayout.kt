package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputLayout(
    value: String,
    modifier: Modifier = Modifier,
    borderWidth: Dp = 1.sdp,
    borderColor: Color = MyStyle.colors.textHijau,
    border: Boolean = true,
    title: String = "",
    hint: String = "",
    prefix: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MyStyle.colors.textBlack,
    isPassword: Boolean = false,
    isNumber: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    isEnable: Boolean = true,
    isExpanse: Boolean = false,
    isDropdown: Boolean = false,
    isRow: Boolean = false,
    isLongInput: Boolean = false,
    maxLength: Int = -1,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(!isPassword) }

    Column(
        modifier = modifier
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = textStyle,
                color = MyStyle.colors.textBlack
            )
        }
        BasicTextField(
            enabled = isEnable,
            value = value,
            onValueChange = {
                if (isNumber) {
                    // filter hanya angka
                    if (it.length <= 10) {
                        val filtered = it.filter { char -> char.isDigit() }
                        onValueChange(filtered)
                    }
                } else {
                    if (maxLength != -1 && it.length > maxLength) {
                        onValueChange(it.substring(0, maxLength))
                    } else {
                        onValueChange(it)
                    }
                }
            },
            modifier = Modifier
                .padding(top = if (title.isNotEmpty()) 2.sdp else 0.sdp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
                .then(
                    if (border) {
                        Modifier.border(
                            width = borderWidth,
                            color = borderColor,
                            shape = RoundedCornerShape(8.sdp)
                        )
                    } else {
                        Modifier  // No border modifier
                    }
                )
                .fillMaxWidth()
                .heightIn(min = 40.sdp, max = if (isLongInput) 120.sdp else 40.sdp),
            singleLine = !isLongInput,
            textStyle = textStyle.copy(
                color = textColor,
                textAlign = if (isRow && prefix.isEmpty()) TextAlign.End else TextAlign.Start
            ),
            cursorBrush = SolidColor(MyStyle.colors.textPrimary),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .padding(
                            start = if (border) 12.sdp else 0.sdp,
                            end = if (isPassword || !border) 0.sdp else 12.sdp
                        ),
                    contentAlignment = if (isRow) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = textStyle.copy(
                                color = MyStyle.colors.neutral60,
                                textAlign = if (isRow) TextAlign.End else TextAlign.Start
                            ),
                        )
                    }
                    if (isPassword) {
                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(0.sdp)
                        ) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                                tint = MyStyle.colors.bgPrimary
                            )
                        }
                    }
                    if (isDropdown) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(0.sdp)
                        ) {
                            Icon(
                                imageVector = if (isExpanse) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
                                contentDescription = if (isExpanse) "Hide password" else "Show password",
                                tint = MyStyle.colors.bgPrimary
                            )
                        }
                    }
                    Row{
                        if (prefix.isNotEmpty()) {
                            Text(
                                text = "$prefix ", style = textStyle.copy(
                                    color = MyStyle.colors.textBlack
                                )
                            )
                        }
                        Box(modifier = if (prefix.isNotEmpty() && isRow) Modifier.width(IntrinsicSize.Min) else Modifier,) {
                            innerTextField()  // No minimum width constraints
                        }
                    }
                }
            },
            keyboardOptions = keyboardOptions.copy(
                keyboardType =
                if (isNumber) {
                    KeyboardType.Number
                } else if (isPassword) {
                    KeyboardType.Password
                } else {
                    keyboardOptions.keyboardType
                }
            ),
            visualTransformation =
            if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )
    }
}


@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun InputLayoutPreview() {
    var username by rememberSaveable { mutableStateOf("kepiting segar, kepiting beku, hingga olahan kepiting seperti daging kepiting kalengan atau produk siap saji seperti kepiting saus padang, kepiting asam manis, dan lain-lain.kepiting segar, kepiting beku, hingga olahan kepiting seperti daging kepiting kalengan atau produk siap saji seperti kepiting saus padang, kepiting asam manis, dan lain-lain.kepiting segar, kepiting beku, hingga olahan kepiting seperti daging kepiting kalengan atau produk siap saji seperti kepiting saus padang, kepiting asam manis, dan lain-lain.") }
    var password by rememberSaveable { mutableStateOf("") }

    RemangokBabelTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MyStyle.colors.bgSecondary)
        ) {
            InputLayout(
                title = "Username",
                value = username,
                hint = "Username",
                border = false,
                isLongInput = true,
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(top = 12.sdp),
                onValueChange = {
                    username = it
                }
            )
            InputLayout(
                title = "Password",
                value = password,
                hint = "value",
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .padding(top = 12.sdp),
                onValueChange = {
                    password = it
                },
                isPassword = true
            )
        }
    }
}