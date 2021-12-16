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
//    primary = Primary,
//    onPrimary = OnPrimary,
//    secondary = Secondary,
//    onSecondary = OnSecondary,
//    background = Background,
//    onBackground = OnBackground,
//    surface = Surface,
//    onSurface = OnSurface,
)

//private val LightColorPalette = lightColors(
//    primary = Purple500,
//    primaryVariant = Purple700,
//    secondary = Teal200
//
//    /* Other default colors to override
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black,
//    */
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