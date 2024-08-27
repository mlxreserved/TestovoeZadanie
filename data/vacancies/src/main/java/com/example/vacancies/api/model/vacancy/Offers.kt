package com.example.vacancies.api.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Offers(
    val offers: List<Offer>,
    var vacancies: List<Vacancy>
)









