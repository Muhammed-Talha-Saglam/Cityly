package dev.bytecode.cityly.model

import com.google.gson.annotations.SerializedName

data class UrbanAreas (
    @SerializedName("_links")
    val links: Links,
    val count: Long
)