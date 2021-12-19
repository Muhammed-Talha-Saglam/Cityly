package dev.bytecode.cityly.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Scores(
    val categories: List<Category>,
    val summary: String,

    @SerialName("teleport_city_score")
    val teleportCityScore: Double
)