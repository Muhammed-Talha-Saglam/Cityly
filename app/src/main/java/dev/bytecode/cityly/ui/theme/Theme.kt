package dev.bytecode.cityly.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Black,
    secondary = Red300,
    onSecondary = Black,
    error = Red200,
    surface = Surface
)

//private val LightColorPalette = lightColors(
//    primary = ShrinePink100,
//    primaryVariant = ShrinePink500,
//    secondary = ShrinePink50,
//    background = ShrinePink100,
//    surface = ShrinePink10,
//    error = Color(0xffc5032b),
//    onPrimary = ShrinePink900,
//    onSecondary = ShrinePink900,
//    onBackground = ShrinePink900,
//    onSurface = ShrinePink900,
//    onError = ShrinePink10
//)

@Composable
fun CitylyTheme(content: @Composable() () -> Unit) {

    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}