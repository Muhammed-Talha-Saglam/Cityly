package dev.bytecode.cityly.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun drawerContent() {
    Column(Modifier.fillMaxSize()) {
        Text(text = "Drawer")
    }
}