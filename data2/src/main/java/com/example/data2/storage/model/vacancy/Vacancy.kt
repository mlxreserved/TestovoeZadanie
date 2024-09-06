package com.example.data2.storage.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Vacancy(
    val address: Address,
    val appliedNumber: Int? = null,
    val company: String,
    val description: String? = null,
    val experience: Experience,
    val id: String,
    var isFavorite: Boolean,
    val lookingNumber: Int? = null,
    val publishedDate: String,
    val questions: List<String>,
    val responsibilities: String? = null,
    val salary: Salary,
    val schedules: List<String>,
    val title: String
)