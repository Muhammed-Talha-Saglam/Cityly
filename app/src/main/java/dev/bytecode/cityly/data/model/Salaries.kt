package dev.bytecode.cityly.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Salaries(
    val salaries: List<Salary>
)