package com.example.data2.storage.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val house: String,
    val street: String,
    val town: String
)