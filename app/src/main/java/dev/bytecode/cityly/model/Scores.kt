package dev.bytecode.cityly.model

import com.google.gson.annotations.SerializedName

data class Scores(
    val categories: List<Category>,
    val summary: String,

    @SerializedName("teleport_city_score")
    val teleportCityScore: Double
)