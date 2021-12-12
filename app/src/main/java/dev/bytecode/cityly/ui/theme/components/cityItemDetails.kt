package dev.bytecode.cityly.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun cityItemDetails() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Details Page", modifier = Modifier.align(Alignment.Center))
    }
}