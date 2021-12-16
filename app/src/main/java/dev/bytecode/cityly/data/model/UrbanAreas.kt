package dev.bytecode.cityly.data.model

import com.google.gson.annotations.SerializedName

data class UrbanAreas (
    @SerializedName("_links")
    val links: Links,
    val count: Long
)