package dev.bytecode.cityly.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun playground() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
            Text(text = "adfasfas",
                modifier = Modifier
                    .clickable(interactionSource = MutableInteractionSource(), indication = rememberRipple(color = Color.Red)){

                    },
                )
    }
}


@Preview(showSystemUi = true)
@Composable
fun playgroundPreview() {
    MaterialTheme {
        playground()
    }
}