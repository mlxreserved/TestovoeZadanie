package com.example.domain.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class SalaryDomain(
    val full: String,
    val short: String? = null
)