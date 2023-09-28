package com.victor.composecalculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Orange400,
    secondary = Yellow400,
    background = Gray900,
    surface = Gray700,
    onPrimary = Gray200,
    onSecondary = Gray500,
    onBackground = Gray200,
    onSurface = Gray200,
)

private val LightColorPalette = lightColors(
    primary = Orange400,
    secondary = Yellow400,
    background = Gray100,
    surface = Gray200,
    onPrimary = Gray600,
    onSecondary = Gray400,
    onBackground = Gray400,
    onSurface = Gray500,
)

@Composable
fun ComposeCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}