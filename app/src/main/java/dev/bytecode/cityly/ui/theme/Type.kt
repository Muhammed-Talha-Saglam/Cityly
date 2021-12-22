package dev.bytecode.cityly.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.bytecode.cityly.R

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = FontFamily(Font(R.font.montserrat_regular)),
    body1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Black,
    ),
    body2 = TextStyle(
        fontSize = 15.sp,
        color = Black,
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Black,
    ),
)