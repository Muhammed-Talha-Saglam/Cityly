package dev.bytecode.cityly.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Job (
    val id: String,
    val title: String
)