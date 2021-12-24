package dev.bytecode.cityly.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.bytecode.cityly.Routes
import dev.bytecode.cityly.data.Cities
import dev.bytecode.cityly.data.model.Question
import dev.bytecode.cityly.ui.theme.Purple
import dev.bytecode.cityly.ui.theme.Yellow
import dev.bytecode.cityly.viewModels.MainViewModel

@Composable
fun Questions(vm: MainViewModel, navController: NavHostController) {

    if (vm.isLoading.value)
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Purple)
        }
    else
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Preferable")
                Text(text = "0")
                Text(text = "Preferable")
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                for (question in Cities.questions) {
                    ItemQuestion(question, vm)
                }

                TextButton(
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .fillMaxWidth(),
                    onClick = {
                        vm.getUrbanAreas()
                        navController.navigate(Routes.CITY_ITEM_LIST)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Purple)
                ) {
                    Text(text = "OK", style = MaterialTheme.typography.h6.copy(Color.White))
                }
            }

        }


}

@Composable
fun ItemQuestion(question: Question, vm: MainViewModel) {
    val point = remember { mutableStateOf(0f) }

    Card {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = question.criteria1, fontSize = 12.sp)
                Text(text = question.criteria2, fontSize = 12.sp)
            }
            Slider(
                value = point.value,
                onValueChange = {
                    point.value = it
                    question.point = it.toInt()
                    Log.d("Questions", point.value.toString())
                },
                steps = 17,
                valueRange = -8f..8f,
                colors = SliderDefaults.colors(
                    thumbColor = Yellow,
                    activeTrackColor = Purple,
                    inactiveTrackColor = Purple,
                    activeTickColor = Color.Transparent
                )
            )
        }
    }
}