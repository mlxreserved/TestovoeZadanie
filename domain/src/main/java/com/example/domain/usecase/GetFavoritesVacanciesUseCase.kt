package com.example.domain.usecase

import com.example.domain.model.favorite.FavoriteVacancyDomain
import com.example.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesVacanciesUseCase(
    private val favoriteRepository: FavoriteRepository
) {

    fun execute(): Flow<List<FavoriteVacancyDomain>> = favoriteRepository.getFavorites()

}