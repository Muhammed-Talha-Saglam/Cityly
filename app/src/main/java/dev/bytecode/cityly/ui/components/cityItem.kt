package dev.bytecode.cityly.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import dev.bytecode.cityly.MainViewModel
import dev.bytecode.cityly.R
import dev.bytecode.cityly.model.UrbanAreaInfo
import kotlin.math.roundToInt

@Composable
fun CityItem(urbanAreaInfo: UrbanAreaInfo, navController: NavHostController, vm: MainViewModel) {

    Column(
        Modifier
            .clickable {
                vm.selectedUrbanAreaInfo = urbanAreaInfo
                navController.navigate("cityItemDetails")
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
                .height(Dp(240f))
                .padding(bottom = Dp(5f)),
            contentScale = ContentScale.FillWidth
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = urbanAreaInfo.fullName)
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
            style = MaterialTheme.typography.body1
        )
    }

}



