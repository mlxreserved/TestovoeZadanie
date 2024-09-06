package com.example.domain.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class OfferDomain(
    val button: ButtonDomain? = null,
    val id: String? = null,
    val link: String,
    val title: String
)