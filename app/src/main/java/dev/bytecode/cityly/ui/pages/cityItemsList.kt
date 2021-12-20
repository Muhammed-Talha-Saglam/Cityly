package dev.bytecode.cityly.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import dev.bytecode.cityly.R
import dev.bytecode.cityly.Routes
import dev.bytecode.cityly.data.model.Result
import dev.bytecode.cityly.data.model.UrbanAreaInfo
import dev.bytecode.cityly.ui.theme.Orange
import dev.bytecode.cityly.viewModels.MainViewModel
import kotlin.math.roundToInt

@ExperimentalAnimationApi
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
            LazyColumn(
                Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(Dp(10f)),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                itemsIndexed(
                    items = (result.value as Result.Success<List<UrbanAreaInfo>>).data!!,
                    key = { i, city -> city.fullName }
                ) { i, city ->
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


@ExperimentalAnimationApi
@Composable
fun CityItem(
    urbanAreaInfo: UrbanAreaInfo,
    navController: NavHostController,
    vm: MainViewModel,
) {

    Column(
        Modifier
            .padding(5.dp)
            .clickable {
                vm.selectedUrbanAreaInfo.value = urbanAreaInfo
                navController.navigate(Routes.CITY_ITEM_DETAILS)
            }
            .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(10f))
            .border(color = Color.Gray, width = 1.dp, shape = RoundedCornerShape(10f))
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
            modifier = Modifier.fillMaxWidth().padding(horizontal =  10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = urbanAreaInfo.fullName,
                style = MaterialTheme.typography.body1.copy(Color.Black, fontSize = 18.sp),
                )
            Spacer(
                modifier = Modifier
                    .height(Dp(1f))
                    .weight(2f)
            )
            Icon(imageVector = Icons.Default.Star, tint = Orange, contentDescription = "star")
            Text(text = urbanAreaInfo.scores?.teleportCityScore?.roundToInt().toString(), color = Color.Black)
        }
        Spacer(modifier = Modifier.height(Dp(4f)))
        Text(
            text = urbanAreaInfo.scores?.summary?.replace("  ", "")!!.replace("\n", "")
                .replace("<p>", "").replace("</p>", "").replace("<b>", "").replace("</b>", ""),
            modifier = Modifier.padding( horizontal = 10.dp),
            style = MaterialTheme.typography.body2.copy(Color.Black, fontSize = 16.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }

}



