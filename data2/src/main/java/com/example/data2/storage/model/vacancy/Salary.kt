package com.example.data2.storage.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Salary(
    val full: String,
    val short: String? = null
)