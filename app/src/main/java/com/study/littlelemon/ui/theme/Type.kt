package com.study.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.study.littlelemon.R

val MarkaziRegular = FontFamily(
    Font(R.font.markazi_regular)
)

val KarlaRegular = FontFamily(
    Font(R.font.karla_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = KarlaRegular,
        fontSize = 20.sp,
        color = Color.Black,
        fontWeight = FontWeight.ExtraBold
    ),
    titleMedium = TextStyle(
        fontFamily = KarlaRegular,
        fontSize = 18.sp,
        color = Color.Black,
        fontWeight = FontWeight.ExtraBold
    ),
    headlineLarge = TextStyle(
        fontFamily = KarlaRegular,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontFamily = KarlaRegular,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),
    displayLarge = TextStyle(
        fontFamily = MarkaziRegular,
        fontSize = 70.sp,
        color = Little_Lemon_Yellow
    ),
    displayMedium = TextStyle(
        fontFamily = MarkaziRegular,
        fontSize = 45.sp,
        color = Color.White
    ),
    displaySmall = TextStyle(
        fontFamily = KarlaRegular,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = KarlaRegular,
        fontSize = 12.sp,
        fontWeight = FontWeight.W700
    ),
    bodyMedium = TextStyle(
        fontFamily = KarlaRegular,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = KarlaRegular,
        fontSize = 18.sp
    )
)