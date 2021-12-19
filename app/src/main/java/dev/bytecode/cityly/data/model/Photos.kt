package dev.bytecode.cityly.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Photos(val photos: List<Photo>)