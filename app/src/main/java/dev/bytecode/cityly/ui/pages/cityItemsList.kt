package dev.bytecode.cityly.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import dev.bytecode.cityly.R
import dev.bytecode.cityly.Routes
import dev.bytecode.cityly.data.model.Result
import dev.bytecode.cityly.data.model.UrbanAreaInfo
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
            LazyColumn(Modifier.fillMaxSize(), state = listState, contentPadding = PaddingValues(Dp(10f)), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                itemsIndexed(
                    items = (result.value as Result.Success<List<UrbanAreaInfo>>).data!!,
                    key = {i ,city -> city.fullName }
                    ) { i, city ->
                    CityItem(city, i, navController, vm)
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
    index: Int,
    navController: NavHostController,
    vm: MainViewModel
) {
    val offset = if (index%2==0) -1 else 1

    val transitionState = remember{ MutableTransitionState(false) }
    LaunchedEffect(true) {
        transitionState.targetState = true
    }


    AnimatedVisibility(visibleState = transitionState, enter = slideInHorizontally({it*offset}, tween(1000)) + fadeIn(0f,tween(1000))) {
        Column(
            Modifier
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = urbanAreaInfo.fullName, style = MaterialTheme.typography.body1.copy(color = Color.Cyan), textDecoration = TextDecoration.Underline)
                Spacer(
                    modifier = Modifier
                        .height(Dp(1f))
                        .weight(2f)
                )
                Icon(imageVector = Icons.Default.Star, tint = Color.Yellow, contentDescription = "star")
                Text(text = urbanAreaInfo.scores.teleportCityScore.roundToInt().toString())
            }
            Spacer(modifier = Modifier.height(Dp(4f)))
            Text(
                text = urbanAreaInfo.scores.summary.replace("  ", "").replace("\n", "")
                    .replace("<p>", "").replace("</p>", "").replace("<b>", "").replace("</b>", ""),
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}



