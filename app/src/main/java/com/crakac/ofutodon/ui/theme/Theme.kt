package com.crakac.ofutodon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue700,
    secondary = Blue500,
    background = DeepBlack,
    surface = DeepBlue,
    onSurface = White50,
    onSecondary = White100,
)

private val LightColorPalette = lightColors(
    primary = Blue500,
    surface = White100,
    onSurface = DeepBlack
)

@Composable
fun OfutodonTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}