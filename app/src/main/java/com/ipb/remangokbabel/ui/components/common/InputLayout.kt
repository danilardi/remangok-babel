package com.ipb.remangokbabel.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme
import ir.kaaveh.sdpcompose.sdp

@Composable
fun InputLayout(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    hint: String = "",
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(!isPassword) }

    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MyStyle.typography.xssNormal,
            color = MyStyle.colors.textBlack
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(top = 2.sdp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
                .border(
                    width = 1.sdp,
                    color = MyStyle.colors.textHijau,
                    shape = RoundedCornerShape(8.sdp)
                )
                .fillMaxWidth()
                .height(40.sdp),
            singleLine = true,
            textStyle = MyStyle.typography.xssNormal.copy(
                color = MyStyle.colors.textPrimary
            ),
            cursorBrush = SolidColor(MyStyle.colors.textPrimary),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.sdp, end = if (isPassword) 0.sdp else 12.sdp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = MyStyle.typography.xssLight.copy(
                                color = MyStyle.colors.neutral60
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
                                tint = MyStyle.colors.backgroundPrimary
                            )
                        }
                    }
                    innerTextField()
                }
            },
            keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else keyboardOptions,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun InputLayoutPreview() {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    RemangokBabelTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MyStyle.colors.backgroundSecondary)
        ) {
            InputLayout(
                title = "Username",
                value = username,
                hint = "Username",
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