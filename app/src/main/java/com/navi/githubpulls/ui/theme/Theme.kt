package com.navi.githubpulls.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion

private val DarkColorPalette = darkColors(
    primary = darkGrey,
    primaryVariant = lightGrey,
    secondary = darkGrey,
    background = darkGrey,
    surface = darkGrey,
    secondaryVariant = whiteGrey
)

private val LightColorPalette = lightColors(
    primary = white,
    primaryVariant = white,
    secondary = white,
    background = white,
    surface = white,
    secondaryVariant = textGray

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
fun GithubPullsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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