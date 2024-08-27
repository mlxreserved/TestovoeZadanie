package com.example.vacancies.api.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val house: String,
    val street: String,
    val town: String
)