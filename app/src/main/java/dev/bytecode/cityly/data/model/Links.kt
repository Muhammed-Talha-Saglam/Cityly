package dev.bytecode.cityly.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Links (
    @SerialName("ua:item")
    val uaItem: List<UaItem>
)