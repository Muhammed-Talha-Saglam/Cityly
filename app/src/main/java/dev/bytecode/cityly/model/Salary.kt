package dev.bytecode.cityly.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class Salary(
    val job: Job,
    @SerializedName("salary_percentiles")
    val salaryPercentiles: SalaryPercentiles
)