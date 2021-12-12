package dev.bytecode.cityly.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import dev.bytecode.cityly.model.UrbanAreaInfo
import dev.bytecode.cityly.utilities.HtmlText
import kotlin.math.roundToInt

@Composable
fun CityItem(city: UrbanAreaInfo, navController: NavHostController) {
    Column(
        Modifier
            .clickable { navController.navigate("cityItemDetails") }
            .padding(vertical = Dp(10f))
            .background(color = Color.Yellow, shape = RoundedCornerShape(10f))
            .padding(
                Dp(4f)
            )) {
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



