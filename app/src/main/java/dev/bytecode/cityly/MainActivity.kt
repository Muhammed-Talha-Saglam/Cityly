package dev.bytecode.cityly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.bytecode.cityly.ui.components.cityItemDetails
import dev.bytecode.cityly.ui.components.cityItemsList
import dev.bytecode.cityly.ui.components.drawerContent
import dev.bytecode.cityly.ui.theme.CitylyTheme
import dev.bytecode.cityly.ui.theme.Purple
import dev.bytecode.cityly.ui.theme.Yellow
import dev.bytecode.cityly.viewModels.MainViewModel

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitylyTheme {
                // A surface container using the 'background' color from the theme
                HomePage()

            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun HomePage() {

    val vm: MainViewModel = hiltViewModel()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Purple,
                title = {
                    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "CITY.LY", textAlign = TextAlign.Center, color = Yellow, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                },
            )
        },
        drawerContent = { drawerContent() },
    ) {
        NavHost(navController = navController, startDestination = Routes.CITY_ITEM_LIST) {
            composable(Routes.CITY_ITEM_LIST) { cityItemsList( vm = vm,navController =  navController) }
            composable(Routes.CITY_ITEM_DETAILS) { cityItemDetails(vm) }
        }
    }

}

class Routes {
    companion object {
        val CITY_ITEM_LIST = "cityItemsList"
        val CITY_ITEM_DETAILS = "cityItemDetails"
    }
}
