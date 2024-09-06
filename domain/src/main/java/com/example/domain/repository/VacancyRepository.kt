package com.example.domain.repository

import com.example.domain.model.vacancy.OffersDomain

interface VacancyRepository {
    suspend fun getVacancies(): OffersDomain
}