package dev.bytecode.cityly.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UrbanAreas (
    @SerialName("_links")
    val links: Links,
    val count: Long
)