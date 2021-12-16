package dev.bytecode.cityly.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.bytecode.cityly.viewModels.MainViewModel
import dev.bytecode.cityly.data.model.Result
import dev.bytecode.cityly.data.model.UrbanAreaInfo

@Composable
fun cityItemsList(vm: MainViewModel, navController: NavHostController) {

    val result = vm.result.observeAsState()
    val listState = rememberLazyListState()

    when (result.value) {
        is Result.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is Result.Success -> {
            LazyColumn(Modifier.fillMaxSize(), state = listState, contentPadding = PaddingValues(Dp(10f)), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(
                    (result.value as Result.Success<List<UrbanAreaInfo>>).data!!,
                    key = { city -> city.fullName }) { city ->
                    CityItem(city, navController, vm)
                }
            }
        }
        is Result.Error -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "${(result.value as Result.Error<List<UrbanAreaInfo>>).message}")
                Button(
                    onClick = { vm.getUrbanAreas() }) {
                    Text(text = "TRY AGAIN")
                }
            }
        }
    }

}