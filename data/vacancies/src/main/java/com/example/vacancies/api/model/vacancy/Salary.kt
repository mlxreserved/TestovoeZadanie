package com.example.vacancies.api.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Salary(
    val full: String,
    val short: String? = null
)