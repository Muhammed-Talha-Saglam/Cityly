package dev.bytecode.cityly.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Salary(
    val job: Job,
    @SerialName("salary_percentiles")
    val salaryPercentiles: SalaryPercentiles
)