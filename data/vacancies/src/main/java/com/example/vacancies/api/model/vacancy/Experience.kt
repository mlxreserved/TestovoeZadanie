package com.example.vacancies.api.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Experience(
    val previewText: String,
    val text: String
)