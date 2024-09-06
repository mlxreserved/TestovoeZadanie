package com.example.data2.storage.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Experience(
    val previewText: String,
    val text: String
)