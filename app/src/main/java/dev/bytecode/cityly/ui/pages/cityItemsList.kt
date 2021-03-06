package dev.bytecode.cityly.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import dev.bytecode.cityly.R
import dev.bytecode.cityly.Routes
import dev.bytecode.cityly.data.model.Result
import dev.bytecode.cityly.data.model.UrbanAreaInfo
import dev.bytecode.cityly.ui.theme.Orange
import dev.bytecode.cityly.ui.theme.Purple
import dev.bytecode.cityly.utilities.removeTag
import dev.bytecode.cityly.viewModels.MainViewModel

@Composable
fun cityItemsList(vm: MainViewModel, navController: NavHostController) {

    val result = vm.result.observeAsState()
    val listState = rememberLazyListState()

    when (result.value) {
        is Result.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Purple
                )
            }
        }
        is Result.Success -> {
            LazyColumn(
                Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(Dp(10f)),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (result.value) {
                    is Result.Loading -> {
                        item {
                            Box(Modifier.fillMaxSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center),
                                    color = Purple
                                )
                            }
                        }
                    }
                    is Result.Success -> {
                        item {
                            Text(text = "Best cities for your choices")
                        }
                        itemsIndexed(
                            items = (result.value as Result.Success<List<UrbanAreaInfo>>).data!!,
                        ) { i, city ->
                            CityItem(city, navController, vm)
                        }

                        item {
                            TextButton(
                                modifier = Modifier
                                    .padding(vertical = 15.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    vm.getUrbanAreas()
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Purple)
                            ) {
                                Text(
                                    text = "SEE MORE",
                                    style = MaterialTheme.typography.h6.copy(Color.White)
                                )
                            }
                        }
                    }
                    else -> {
                        item {
                            TextButton(
                                modifier = Modifier
                                    .padding(vertical = 15.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    vm.getUrbanAreas()
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Purple)
                            ) {
                                Text(
                                    text = "ERROR\nTRY AGAIN",
                                    style = MaterialTheme.typography.h6.copy(Color.White)
                                )
                            }
                        }
                    }
                }
            }
        }
        is Result.Error -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${(result.value as Result.Error<List<UrbanAreaInfo>>).message}",
                    color = Color.Black
                )
                Button(
                    onClick = { vm.getUrbanAreas() }) {
                    Text(text = "TRY AGAIN")
                }
            }
        }
    }

}


@Composable
fun CityItem(
    urbanAreaInfo: UrbanAreaInfo,
    navController: NavHostController,
    vm: MainViewModel,
) {

    Card(elevation = 5.dp) {
        Column(
            Modifier
                .padding(5.dp)
                .clickable {
                    vm.selectedUrbanAreaInfo.value = urbanAreaInfo
                    navController.navigate(Routes.CITY_ITEM_DETAILS)
                }
                .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(10f))
                .padding(
                    Dp(4f)
                )) {

            Image(
                painter = rememberImagePainter(
                    data = urbanAreaInfo.imgUrl
                ) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image_placeholder)
                },
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dp(150f))
                    .padding(bottom = Dp(5f)),
                contentScale = ContentScale.FillWidth
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = urbanAreaInfo.fullName,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    modifier = Modifier
                        .height(Dp(1f))
                        .weight(2f)
                )
                Icon(imageVector = Icons.Default.Star, tint = Orange, contentDescription = "star")
                Text(text = urbanAreaInfo.result.toString(), color = Color.Black)
            }
            Spacer(modifier = Modifier.height(Dp(4f)))
            Text(
                text = removeTag(urbanAreaInfo.scores?.summary),
                modifier = Modifier.padding(horizontal = 10.dp),
                style = MaterialTheme.typography.body2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}



