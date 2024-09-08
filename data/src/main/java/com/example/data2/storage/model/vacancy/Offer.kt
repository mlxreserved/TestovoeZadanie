package com.example.data2.storage.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Offer(
    val button: Button? = null,
    val id: String? = null,
    val link: String,
    val title: String
)