package com.example.domain.model.vacancy

import kotlinx.serialization.Serializable

@Serializable
data class OffersDomain(
    val offers: List<OfferDomain>,
    var vacancies: List<VacancyDomain>
)









