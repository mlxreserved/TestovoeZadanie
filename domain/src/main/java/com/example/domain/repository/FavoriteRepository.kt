package com.example.domain.repository

import com.example.domain.model.favorite.FavoriteVacancyDomain
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun addToFavorite(favorite: FavoriteVacancyDomain)

    fun deleteFromFavorite(favorite: FavoriteVacancyDomain)

    fun getFavorites(): Flow<List<FavoriteVacancyDomain>>

}