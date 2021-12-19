package dev.bytecode.cityly.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val color: String,
    val name: String,

    @SerialName("score_out_of_10")
    val scoreOutOf10: Double
)