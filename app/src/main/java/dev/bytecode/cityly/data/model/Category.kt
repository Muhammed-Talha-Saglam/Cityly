package dev.bytecode.cityly.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    val color: String,
    val name: String,

    @SerializedName("score_out_of_10")
    val scoreOutOf10: Double
)