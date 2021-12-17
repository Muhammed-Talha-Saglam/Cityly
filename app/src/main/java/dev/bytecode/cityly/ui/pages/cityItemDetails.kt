package dev.bytecode.cityly.ui.components

import androidx.compose.foundation.*
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.bytecode.cityly.R
import dev.bytecode.cityly.data.model.Category
import dev.bytecode.cityly.data.model.Salary
import dev.bytecode.cityly.data.model.UrbanAreaInfo
import dev.bytecode.cityly.utilities.HexToJetpackColor
import dev.bytecode.cityly.viewModels.MainViewModel

@Composable
fun cityItemDetails(vm: MainViewModel) {

    val selectedCity by vm.selectedUrbanAreaInfo

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dp(10f)), verticalArrangement = Arrangement.spacedBy(Dp(25f))
    ) {
        Image(
            painter = rememberImagePainter(
                data = selectedCity?.imgUrl
            ) {
                crossfade(true)
                placeholder(R.drawable.ic_image_placeholder)
            },
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .height(Dp(240f))
                .padding(bottom = Dp(5f))
                .clip(RoundedCornerShape(25.dp)),
            contentScale = ContentScale.FillWidth
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${selectedCity?.fullName}",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "${selectedCity?.continent}",
                style = MaterialTheme.typography.h6.copy(color = Color.Green)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Scores", style = MaterialTheme.typography.h6.copy(color = Color.Cyan), textDecoration = TextDecoration.Underline)
            Text(
                text = "Overall Score: ${selectedCity?.scores?.teleportCityScore?.toInt()}",
                style = MaterialTheme.typography.h6
            )
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Dp(12f))) {
            items(selectedCity?.scores!!.categories) { category -> ItemCategory(category) }
        }
        Text(text = "Popular Jobs", style = MaterialTheme.typography.h6.copy(color = Color.Cyan), textDecoration = TextDecoration.Underline)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Dp(5f))) {
            items(selectedCity?.salaries!!.salaries) { salary ->
                ItemJob(salary)
            }
        }

        Text(text = "Other cities that you may like", style = MaterialTheme.typography.h6.copy(color = Color.Cyan), textDecoration = TextDecoration.Underline)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Dp(5f))) {
            items(vm.listOfUrbanAreaInfo) { urbanAreaInfo ->
                if (urbanAreaInfo != selectedCity)
                ItemOtherCity(urbanAreaInfo, vm)
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

@Composable
fun ItemJob(salary: Salary) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(12.dp))
            .padding(Dp(10f))
    ) {
        Text(text = salary.job.title, style = MaterialTheme.typography.h6, color = Color.Red)
        Spacer(modifier = Modifier.height(Dp(4f)))
        Text(text = "Salary", style = MaterialTheme.typography.body1, textDecoration = TextDecoration.Underline)
        Text(
            text = "Median: ${salary.salaryPercentiles.percentile50.toInt()} $",
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "Max: ${salary.salaryPercentiles.percentile75.toInt()} $",
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun ItemOtherCity(urbanAreaInfo: UrbanAreaInfo, vm: MainViewModel) {
    Column(modifier = Modifier.width(150.dp).background(MaterialTheme.colors.surface, shape = RoundedCornerShape(10.dp)).clickable { vm.selectedUrbanAreaInfo.value = urbanAreaInfo }.padding(12.dp),) {
        Image(
            painter = rememberImagePainter(
                data = urbanAreaInfo.imgUrl
            ) {
                crossfade(true)
                placeholder(R.drawable.ic_image_placeholder)
            },
            contentDescription = "image",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Fit
        )

        Text(text = urbanAreaInfo.fullName, style = MaterialTheme.typography.body1, color = Color.Red)
        Text(text = urbanAreaInfo.scores.summary.replace("  ", "").replace("\n", "")
            .replace("<p>", "").replace("</p>", "").replace("<b>", "").replace("</b>", ""), style = MaterialTheme.typography.body2, maxLines = 2, overflow = TextOverflow.Ellipsis)

    }
}
