package com.ipb.remangokbabel.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.kaaveh.sdpcompose.ssp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

class MyTypography {
    private val baseFont: TextStyle
        @Composable get() = TextStyle(
            fontFamily = FontFamily.Default,
            letterSpacing = 0.05.sp,
            color = MyStyle.myColors.textBlack
        )

    /* XSSSS */

    val xssssLight: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 6.ssp,
            fontWeight = FontWeight.Light,
            lineHeight = 9.ssp
        )
    val xssssNormal: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 6.ssp,
            fontWeight = FontWeight.Normal,
            lineHeight = 9.ssp
        )
    val xssssMedium: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 6.ssp,
            fontWeight = FontWeight.Medium,
            lineHeight = 9.ssp
        )
    val xssssSemibold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 6.ssp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 9.ssp
        )
    val xssssBold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 6.ssp,
            fontWeight = FontWeight.Bold,
            lineHeight = 9.ssp
        )

    /* XSSS */

    val xsssLight: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 8.ssp,
            fontWeight = FontWeight.Light,
            lineHeight = 12.ssp
        )
    val xsssNormal: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 8.ssp,
            fontWeight = FontWeight.Normal,
            lineHeight = 12.ssp
        )
    val xsssMedium: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 8.ssp,
            fontWeight = FontWeight.Medium,
            lineHeight = 12.ssp
        )
    val xsssSemibold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 8.ssp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 12.ssp
        )
    val xsssBold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 8.ssp,
            fontWeight = FontWeight.Bold,
            lineHeight = 12.ssp
        )

    /* XSS */

    val xssLight: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 10.ssp,
            fontWeight = FontWeight.Light,
            lineHeight = 15.ssp
        )
    val xssNormal: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 10.ssp,
            fontWeight = FontWeight.Normal,
            lineHeight = 15.ssp
        )
    val xssMedium: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 10.ssp,
            fontWeight = FontWeight.Medium,
            lineHeight = 15.ssp
        )
    val xssSemibold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 10.ssp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 15.ssp
        )
    val xssBold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 10.ssp,
            fontWeight = FontWeight.Bold,
            lineHeight = 15.ssp
        )

    /* XS */

    val xsLight: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 12.ssp,
            fontWeight = FontWeight.Light,
            lineHeight = 18.ssp
        )
    val xsNormal: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 12.ssp,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.ssp
        )
    val xsMedium: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 12.ssp,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.ssp
        )
    val xsSemibold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 12.ssp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 18.ssp
        )
    val xsBold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 12.ssp,
            fontWeight = FontWeight.Bold,
            lineHeight = 18.ssp
        )

    /* SM */

    val smLight: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 14.ssp,
            fontWeight = FontWeight.Light,
            lineHeight = 21.ssp
        )
    val smNormal: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 14.ssp,
            fontWeight = FontWeight.Normal,
            lineHeight = 21.ssp
        )
    val smMedium: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 14.ssp,
            fontWeight = FontWeight.Medium,
            lineHeight = 21.ssp
        )
    val smSemibold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 14.ssp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 21.ssp
        )
    val smBold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 14.ssp,
            fontWeight = FontWeight.Bold,
            lineHeight = 21.ssp
        )

    /* BASE */

    val baseLight: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 16.ssp,
            fontWeight = FontWeight.Light,
            lineHeight = 24.ssp
        )
    val baseNormal: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 16.ssp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.ssp
        )
    val baseMedium: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 16.ssp,
            fontWeight = FontWeight.Medium,
            lineHeight = 24.ssp
        )
    val baseSemibold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 16.ssp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 24.ssp
        )
    val baseBold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 16.ssp,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.ssp
        )

    /* LG */

    val lgLight: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 18.ssp,
            fontWeight = FontWeight.Light,
            lineHeight = 27.ssp
        )
    val lgNormal: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 18.ssp,
            fontWeight = FontWeight.Normal,
            lineHeight = 27.ssp
        )
    val lgMedium: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 18.ssp,
            fontWeight = FontWeight.Medium,
            lineHeight = 27.ssp
        )
    val lgSemibold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 18.ssp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 27.ssp
        )
    val lgBold: TextStyle
        @Composable get() = baseFont.copy(
            fontSize = 18.ssp,
            fontWeight = FontWeight.Bold,
            lineHeight = 27.ssp
        )
}