package dev.bytecode.cityly

import dev.bytecode.cityly.model.UrbanAreaInfo

data class MainUiState(
    var dataToDisplayOnScreen: List<UrbanAreaInfo> = emptyList(),
    var loading: Boolean = false
)