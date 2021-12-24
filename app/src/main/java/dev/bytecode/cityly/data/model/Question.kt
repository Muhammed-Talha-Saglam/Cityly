package dev.bytecode.cityly.data.model

data class Question(
    val criteria1: String,
    val criteria2: String,
    var point: Int = 0,
)