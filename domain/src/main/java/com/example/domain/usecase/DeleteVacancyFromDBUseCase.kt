package com.example.domain.usecase

import com.example.domain.model.favorite.FavoriteVacancyDomain
import com.example.domain.repository.FavoriteRepository

class DeleteVacancyFromDBUseCase(
    private val favoriteRepository: FavoriteRepository
) {

    fun execute(favorite: FavoriteVacancyDomain){
        favoriteRepository.deleteFromFavorite(favorite)
    }

}