package com.example.mycleaninglog.ui.theme

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = RegularBlue,
    primaryVariant = LightBlue,
    secondary = Tan,
    onSecondary = Color.White,
    background = DarkGray,
    onBackground = Color.White,
    surface = DarkBlue,
    onSurface = Color.White,
)

private val LightColorPalette = lightColors(
    primary = RegularBlue,
    primaryVariant = LightBlue,
    secondary = Tan,
    onSecondary = Color.Black,
    background = OffWhite,
    onBackground = Color.Black,
    surface =  DarkBlue,
    onSurface = Color.Black,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyCleaningLogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
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