package dev.bytecode.cityly.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UrbanAreaInfo (
    val continent: String,

    @SerialName("full_name")
    val fullName: String,
    val slug: String,

    var salaries: Salaries? = null,
    var scores: Scores? = null,
    var imgUrl: String? = null
)

