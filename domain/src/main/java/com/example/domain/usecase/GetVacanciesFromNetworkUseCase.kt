package com.example.domain.usecase

import com.example.domain.repository.VacancyRepository
import com.example.domain.model.vacancy.OffersDomain

class GetVacanciesFromNetworkUseCase constructor(
    private val vacancyRepository: VacancyRepository
) {
    suspend fun execute(): OffersDomain = vacancyRepository.getVacancies()
}