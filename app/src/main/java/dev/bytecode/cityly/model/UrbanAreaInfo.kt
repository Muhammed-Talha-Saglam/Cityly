package dev.bytecode.cityly.model

import com.google.gson.annotations.SerializedName

data class UrbanAreaInfo (
    val continent: String,
    val mayor: String,
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    var salaries: Salaries,
    var scores: Scores
)
