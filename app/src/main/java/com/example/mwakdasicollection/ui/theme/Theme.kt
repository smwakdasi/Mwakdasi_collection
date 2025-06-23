package com.example.mwakdasicollection.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFF1EB980) /* TODO @mwakdasi edit the color */,
    secondary = Color(0xFF546E7A)/* TODO @mwakdasi edit the color */
)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF1EB980)/* your color */,
    secondary = Color(0xFF546E7A)/* your color */
)

@Composable
fun MwakdasiCollectionTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = Typography()/* your typography */,
        shapes = Shapes()/* your shapes */,
        content = content
    )
}