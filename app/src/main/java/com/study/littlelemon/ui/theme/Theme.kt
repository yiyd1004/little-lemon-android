package com.study.littlelemon.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LittleLemonColorScheme = lightColorScheme(
    primary = Little_Lemon_Yellow,
    secondary = Little_Lemon_Green,
    surface = Color.White,
    surfaceVariant = Little_Lemon_Gray_100,
    onSurface = Color.Black,
    onSurfaceVariant = Little_Lemon_Gray_200
)

@Composable
fun LittleLemonTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LittleLemonColorScheme,
        typography = Typography,
        content = content
    )
}