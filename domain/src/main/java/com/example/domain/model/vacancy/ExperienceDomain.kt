package com.example.domain.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class ExperienceDomain(
    val previewText: String,
    val text: String
)