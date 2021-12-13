package dev.bytecode.cityly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.bytecode.cityly.ui.theme.CitylyTheme
import dev.bytecode.cityly.ui.theme.components.cityItemDetails
import dev.bytecode.cityly.ui.theme.components.cityItemsList
import dev.bytecode.cityly.ui.theme.components.drawerContent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitylyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomePage()
                }
            }
        }
    }
}

@Composable
fun HomePage() {

   // val vm = viewModel<MainViewModel>()
    val vm: MainViewModel = hiltViewModel()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            modifier = Modifier.clickable { scope.launch { scaffoldState.drawerState.apply { if (isClosed) open() else close() } } })
                        Text(text = "City.Ly", textAlign = TextAlign.Center)
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, modifier = Modifier.padding(end = Dp(4f)))
                    }
                },
            )
        },
        drawerContent = { drawerContent() },
//        floatingActionButton = {
//            FloatingActionButton(onClick = { }) {
//                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
//            }
//        }

    ) {
        NavHost(navController = navController, startDestination = "cityItemsList") {
            composable("cityItemsList") {cityItemsList( vm = vm,navController =  navController)}
            composable("cityItemDetails") {cityItemDetails(vm)}
        }
    }

}

