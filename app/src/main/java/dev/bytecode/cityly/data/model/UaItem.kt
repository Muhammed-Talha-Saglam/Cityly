package dev.bytecode.cityly.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UaItem (
    val href: String,
    val name: String
)