package com.crakac.ofutodon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.crakac.ofutodon.util.LocalPainterResource
import com.crakac.ofutodon.util.rememberPainterCache

private val DarkColorPalette = darkColors(
    primary = Blue700,
    secondary = Blue500,
    background = DeepBlack,
    surface = DeepBlue,
    onSurface = White50,
    onPrimary = White50,
    onSecondary = White50,
)

private val LightColorPalette = lightColors(
    primary = Blue500,
    surface = White100,
    onSurface = DeepBlack,
)

@Composable
fun OfutodonTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val painterCache = rememberPainterCache()
    CompositionLocalProvider(
        LocalContentAlpha provides 1f,
        LocalPainterResource providesDefault painterCache,
    ) {
        MaterialTheme(
            colors = if (darkTheme) DarkColorPalette else LightColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}
