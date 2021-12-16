package dev.bytecode.cityly.data.model

import com.google.gson.annotations.SerializedName

data class SalaryPercentiles(
    val percentile25: Double,

    @SerializedName("percentile_50")
    val percentile50: Double,

    @SerializedName("percentile_75")
    val percentile75: Double
)