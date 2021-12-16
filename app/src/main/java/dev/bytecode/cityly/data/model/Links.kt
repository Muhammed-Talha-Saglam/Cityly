package dev.bytecode.cityly.data.model

import com.google.gson.annotations.SerializedName

data class Links (
    @SerializedName("ua:item")
    val uaItem: List<UaItem>
)