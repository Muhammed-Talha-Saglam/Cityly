package dev.bytecode.cityly.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import dev.bytecode.cityly.model.states.MainUiState

@Composable
fun cityItemsList(uiState: MutableState<MainUiState>, navController: NavHostController) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(Dp(10f))) {
        if (uiState.value.loading) {
            item {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        } else {
            items(uiState.value.dataToDisplayOnScreen, key = { city -> city.fullName }) { city ->
                CityItem(city, navController)
            }
        }
    }
}