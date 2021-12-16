package dev.bytecode.cityly.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import dev.bytecode.cityly.viewModels.MainViewModel
import dev.bytecode.cityly.data.model.Category
import dev.bytecode.cityly.utilities.HexToJetpackColor

@Composable
fun cityItemDetails(vm: MainViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dp(10f)), verticalArrangement = Arrangement.spacedBy(Dp(25f))
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "${vm.selectedUrbanAreaInfo?.fullName}",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "${vm.selectedUrbanAreaInfo?.continent}",
                style = MaterialTheme.typography.h6
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Scores", style = MaterialTheme.typography.h6)
            Text(
                text = "Overall Score: ${vm.selectedUrbanAreaInfo?.scores?.teleportCityScore?.toInt()}",
                style = MaterialTheme.typography.h6
            )
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Dp(12f))) {
            items(vm.selectedUrbanAreaInfo!!.scores.categories) { category -> ItemCategory(category) }
        }
        Text(text = "Popular Jobs", style = MaterialTheme.typography.h6)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Dp(5f))) {
            items(vm.selectedUrbanAreaInfo!!.salaries.salaries) { salary ->
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.surface)
                        .padding(Dp(2f))
                ) {
                    Text(text = salary.job.title, style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(Dp(4f)))
                    Text(
                        text = "Median: ${salary.salaryPercentiles.percentile50.toInt()}",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "Max: ${salary.salaryPercentiles.percentile75.toInt()}",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Composable
fun ItemCategory(category: Category) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20f, topEnd = 20f))
            .background(color = MaterialTheme.colors.surface)
            .drawBehind {
                drawLine(
                    HexToJetpackColor.getColor(category.color),
                    Offset(0f, size.height),
                    Offset(size.width, size.height),
                    30f
                )
            }
            .padding(start = Dp(8f), end = Dp(8f), bottom = Dp(10f))

    ) {
        Text(text = category.name, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(Dp(4f)))
        Row(verticalAlignment = Alignment.Bottom) {
            Icon(imageVector = Icons.Sharp.Star, contentDescription = "star", tint = Color.Yellow)
            Spacer(modifier = Modifier.width(Dp(4f)))
            Text(
                text = "${category.scoreOutOf10.toInt()}",
                style = MaterialTheme.typography.h6
            )
        }
    }
}


