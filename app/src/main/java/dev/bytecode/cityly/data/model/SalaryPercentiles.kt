package dev.bytecode.cityly.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalaryPercentiles(
    @SerialName("percentile_50")
    val percentile50: Double,

    @SerialName("percentile_75")
    val percentile75: Double
)