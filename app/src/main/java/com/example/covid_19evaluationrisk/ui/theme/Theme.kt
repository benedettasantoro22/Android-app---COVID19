package com.example.covid_19evaluationrisk.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = DarkPurple,
    surface = HonoluluBlue,
    secondary = VistaBlue,
    onPrimary = MountbattenPink,
    onSurface = MountbattenPink,
    background = BlueNCS

)

private val LightColorPalette = lightColors(
    primary = MintGreen,
    surface = TiffanyBlue,
    secondary = PayneGray,
    onPrimary = RichBlack,
    onSurface = RichBlack,
    background = Celeste
    // primaryVariant = Purple700,
    // secondary = Teal200

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
fun Covid19EvaluationRiskTheme(
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