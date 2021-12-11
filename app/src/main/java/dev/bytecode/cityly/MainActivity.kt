package dev.bytecode.cityly

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import dagger.hilt.android.AndroidEntryPoint
import dev.bytecode.cityly.model.UrbanAreaInfo
import dev.bytecode.cityly.ui.theme.CitylyTheme
import dev.bytecode.cityly.utilities.HtmlText
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: MainViewModel by viewModels()
        setContent {
            CitylyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomePage(vm)
                }
            }
        }
    }
}

@Composable
fun HomePage(vm: MainViewModel) {

    var state = vm.uiState
    Log.d("listOfCities", state.toString())

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dp(60f))
                    .background(Color.Black, RoundedCornerShape(0, 0, 40, 40)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Cityly", color = Color.White)
            }
        }

    ) {
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(Dp(10f))) {
            if (state.value.loading) {
                item {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                items(state.value.dataToDisplayOnScreen) { city ->
                    CityItem(city)
                }
            }
        }

    }

}

@Composable
fun CityItem(city: UrbanAreaInfo) {
    Column(Modifier.padding(vertical = Dp(10f)).background(color = Color.Yellow, shape = RoundedCornerShape(10f)).padding(Dp(4f))) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = city.fullName, color = Color.Red)
            Text(text = city.scores.teleportCityScore.roundToInt().toString(), color = Color.Blue)
        }
        Spacer(modifier = Modifier.height(Dp(4f)))
        HtmlText(html = city.scores.summary)
    }
}


