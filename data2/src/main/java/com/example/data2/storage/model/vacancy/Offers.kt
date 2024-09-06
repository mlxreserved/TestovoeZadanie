package com.example.data2.storage.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class Offers(
    val offers: List<Offer>,
    var vacancies: List<Vacancy>
)









