package dev.bytecode.cityly.ui.components

import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import dev.bytecode.cityly.R
import dev.bytecode.cityly.data.model.Category
import dev.bytecode.cityly.data.model.Salary
import dev.bytecode.cityly.data.model.UrbanAreaInfo
import dev.bytecode.cityly.ui.theme.Black
import dev.bytecode.cityly.ui.theme.Orange
import dev.bytecode.cityly.ui.theme.Purple
import dev.bytecode.cityly.ui.theme.White
import dev.bytecode.cityly.utilities.HexToJetpackColor
import dev.bytecode.cityly.utilities.removeTag
import dev.bytecode.cityly.viewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun cityItemDetails(vm: MainViewModel) {

    val selectedCity by vm.selectedUrbanAreaInfo
    val verticalScrollState = rememberScrollState()
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState)
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
                style = MaterialTheme.typography.h6.copy(Color.Black, fontSize = 25.sp)
            )
            Text(
                text = "${selectedCity?.continent}",
                style = MaterialTheme.typography.body2.copy(fontSize = 18.sp, color = Color.Black)
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Summary",
                style = MaterialTheme.typography.h6.copy(Black)
            )
            Text(
                text = removeTag(selectedCity?.scores?.summary),
                style = MaterialTheme.typography.body2.copy(fontSize = 18.sp, color = Color.Black)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Scores", style = MaterialTheme.typography.h6.copy(color = Color.Black))
            Text(
                text = "Overall Score: ${selectedCity?.scores?.teleportCityScore?.toInt()}",
                style = MaterialTheme.typography.h6.copy(color = Color.Black)
            )
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Dp(12f))) {
            selectedCity?.scores?.categories?.let { categories ->
                items(categories) { category -> ItemCategory(category) }
            }
        }
        Text(text = "Popular Jobs", style = MaterialTheme.typography.h6.copy(color = Color.Black))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Dp(5f))) {
            selectedCity?.salaries?.salaries?.let { salaries ->
                items(salaries) { salary ->
                    ItemJob(salary)
                }

            }
        }

        Text(text = "Other cities that you may like", style = MaterialTheme.typography.h6.copy(color = Color.Black))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Dp(5f))) {
            items(vm.listOfUrbanAreaInfo, key = {it.fullName}) { urbanAreaInfo ->
                if (urbanAreaInfo != selectedCity)
                    ItemOtherCity(urbanAreaInfo, vm, verticalScrollState, scope)
            }
        }

    }
}

@Composable
fun ItemCategory(category: Category) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20f, topEnd = 20f))
            .background(color = Purple)
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
        Text(text = category.name, style = MaterialTheme.typography.body1.copy(White))
        Spacer(modifier = Modifier.height(Dp(4f)))
        Row(verticalAlignment = Alignment.Bottom) {
            Icon(imageVector = Icons.Sharp.Star, contentDescription = "star", tint = Orange)
            Spacer(modifier = Modifier.width(Dp(4f)))
            Text(
                text = "${category.scoreOutOf10.toInt()}",
                style = MaterialTheme.typography.h6.copy(White)
            )
        }
    }
}

@Composable
fun ItemJob(salary: Salary) {
    Column(
        modifier = Modifier
            .background(color = Purple, shape = RoundedCornerShape(12.dp))
            .padding(Dp(10f))
    ) {
        Text(text = salary.job.title, style = MaterialTheme.typography.h6.copy(White))
        Spacer(modifier = Modifier.height(Dp(4f)))
        Text(text = "Salary", style = MaterialTheme.typography.body1.copy(White), textDecoration = TextDecoration.Underline)
        Text(
            text = "Median: ${salary.salaryPercentiles.percentile50.toInt()} $",
            style = MaterialTheme.typography.body2.copy(White)
        )
        Text(
            text = "Max: ${salary.salaryPercentiles.percentile75.toInt()} $",
            style = MaterialTheme.typography.body2.copy(White)
        )
    }
}

@Composable
fun ItemOtherCity(
    urbanAreaInfo: UrbanAreaInfo,
    vm: MainViewModel,
    verticalScrollState: ScrollState,
    scope: CoroutineScope
) {

    Column(modifier = Modifier
        .width(150.dp)
        .fillMaxHeight()
        .background(Purple, shape = RoundedCornerShape(10.dp))
        .clickable {
            vm.selectedUrbanAreaInfo.value = urbanAreaInfo
            scope.launch {
                verticalScrollState.animateScrollTo(0, tween(500))
            }
        }
        .padding(12.dp),) {
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

        Text(text = urbanAreaInfo.fullName, style = MaterialTheme.typography.body1.copy(White))
        Text(text = removeTag(urbanAreaInfo.scores?.summary), style = MaterialTheme.typography.body2.copy(White), maxLines = 2, overflow = TextOverflow.Ellipsis)

    }
}
