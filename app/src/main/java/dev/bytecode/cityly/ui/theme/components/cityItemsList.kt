package dev.bytecode.cityly.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import dev.bytecode.cityly.MainViewModel
import dev.bytecode.cityly.core.util.Resource
import dev.bytecode.cityly.model.UrbanAreaInfo

@Composable
fun cityItemsList(vm: MainViewModel, navController: NavHostController) {

    val resources = vm.resource.observeAsState()

    when (resources.value) {
        is Resource.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is Resource.Success -> {
            LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(Dp(10f))) {
                items(
                    (resources.value as Resource.Success<List<UrbanAreaInfo>>).data!!,
                    key = { city -> city.fullName }) { city ->
                    CityItem(city, navController)
                }

            }
        }
        is Resource.Error -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "${(resources.value as Resource.Error<List<UrbanAreaInfo>>).message}")
                Button(
                    onClick = { vm.getUrbanAreas() }) {
                    Text(text = "TRY AGAIN")
                }
            }
        }
    }

}