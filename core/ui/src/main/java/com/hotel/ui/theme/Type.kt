package com.hotel.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


val Headline1 = TextStyle(
    fontSize = 18.sp,
    lineHeight = 21.6.sp,
    fontWeight = FontWeight(500),
    color = Color(0xFF000000),
)

val title = TextStyle(
    fontSize = 22.sp,
    lineHeight = 26.4.sp,
    fontWeight = FontWeight(500),
    color = Color(0xFF000000)
)

val bigTitle = TextStyle(
    fontSize = 30.sp,
    lineHeight = 36.sp,
    fontWeight = FontWeight(600),
    color = Color(0xFF000000)
)

val subtitle = TextStyle(
    fontSize = 16.sp,
    lineHeight = 19.2.sp,
    fontWeight = FontWeight(500),
    color = Color(0xFF000000),
    textAlign = TextAlign.Center,
)

val subtitleLight = TextStyle(
    fontSize = 16.sp,
    lineHeight = 19.2.sp,
    fontWeight = FontWeight(400),
    color = Color(0xFF828796),

    )

val body = TextStyle(
    fontSize = 14.sp,
    lineHeight = 16.8.sp,
    fontWeight = FontWeight(500),
    color = Color(0xFF000000),
)

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