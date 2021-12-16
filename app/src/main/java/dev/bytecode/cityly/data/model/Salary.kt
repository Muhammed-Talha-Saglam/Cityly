package dev.bytecode.cityly.data.model

import com.google.gson.annotations.SerializedName

data class Salary(
    val job: Job,
    @SerializedName("salary_percentiles")
    val salaryPercentiles: SalaryPercentiles
)