package com.example.domain.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class VacancyDomain(
    val address: AddressDomain,
    val appliedNumber: Int? = null,
    val company: String,
    val description: String? = null,
    val experience: ExperienceDomain,
    val id: String,
    var isFavorite: Boolean,
    val lookingNumber: Int? = null,
    val publishedDate: String,
    val questions: List<String>,
    val responsibilities: String? = null,
    val salary: SalaryDomain,
    val schedules: List<String>,
    val title: String
)