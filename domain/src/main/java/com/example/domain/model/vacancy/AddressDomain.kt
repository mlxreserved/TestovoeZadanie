package com.example.domain.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class AddressDomain(
    val house: String,
    val street: String,
    val town: String
)