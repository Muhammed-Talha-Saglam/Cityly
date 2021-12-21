package dev.bytecode.cityly.ui.pages

import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.bytecode.cityly.Routes
import dev.bytecode.cityly.ui.theme.Black
import dev.bytecode.cityly.ui.theme.Purple
import dev.bytecode.cityly.viewModels.MainViewModel

@ExperimentalComposeUiApi
@Composable
fun Questions(vm: MainViewModel, navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
        for ((question, star) in vm.questionRatingMap) {
          ItemQuestion(question, star, vm)
        }

        TextButton(
            modifier = Modifier.padding(vertical = 15.dp).fillMaxWidth(),
            onClick = { navController.navigate(Routes.CITY_ITEM_LIST) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple)
            ) {
            Text(text = "OK", fontSize = 20.sp)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ItemQuestion(question: String, star: Int, vm: MainViewModel) {
    val rating = remember { mutableStateOf(star) }

    Card {
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth() ,verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Text(text = question, color = Black, fontSize = 25.sp)
            RatingBar(rating = rating.value, onRatingChanged = {
                rating.value = it
                vm.updateQuestionRating(question, rating.value)
            })
        }
    }

}



@ExperimentalComposeUiApi
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {

    var selected by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(
        targetValue = if (selected) 72.dp else 64.dp,
        spring(Spring.DampingRatioMediumBouncy)
    )

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                Icons.Default.Star,
                contentDescription = "star",
                modifier = modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                onRatingChanged(i)
                            }
                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i <= rating) Color(0xFFFFD700) else Color(0xFFA2ADB1)
            )
        }
    }
}