package dev.bytecode.cityly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.bytecode.cityly.ui.components.cityItemDetails
import dev.bytecode.cityly.ui.components.cityItemsList
import dev.bytecode.cityly.ui.pages.Questions
import dev.bytecode.cityly.ui.theme.CitylyTheme
import dev.bytecode.cityly.ui.theme.Purple
import dev.bytecode.cityly.viewModels.MainViewModel

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
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 3.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = R.drawable.app_icon), contentDescription = "app icon" )
                    }
                },
                actions = { Icon(
                    modifier = Modifier.clickable { navController.navigate(Routes.QUESTIONS)
                    {
                        launchSingleTop = true
                        popUpTo(Routes.QUESTIONS) {
                            inclusive = true
                        }
                    }
                    }.padding(end = 5.dp).size(30.dp),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "refresh",
                    tint = Color.White
                    )
                }
            )
        },
    ) {
        NavHost(navController = navController, startDestination = Routes.QUESTIONS) {
            composable(Routes.QUESTIONS) { Questions(vm = vm, navController)}
            composable(Routes.CITY_ITEM_LIST) { cityItemsList( vm = vm,navController =  navController) }
            composable(Routes.CITY_ITEM_DETAILS) { cityItemDetails(vm) }
        }
    }

}

class Routes {
    companion object {
        val QUESTIONS = "questions"
        val CITY_ITEM_LIST = "cityItemsList"
        val CITY_ITEM_DETAILS = "cityItemDetails"
    }
}
