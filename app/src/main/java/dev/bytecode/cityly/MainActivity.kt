package dev.bytecode.cityly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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

    var uiState = vm.uiState

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
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
            }
        }

    ) {
        NavHost(navController = navController, startDestination = "cityItemsList") {
            composable("cityItemsList") {cityItemsList(uiState, navController)}
            composable("cityItemDetails") { cityItemDetails()}
        }
    }

}

