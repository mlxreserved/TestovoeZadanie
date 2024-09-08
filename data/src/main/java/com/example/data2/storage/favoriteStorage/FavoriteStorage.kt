package com.example.data2.storage.favoriteStorage

import com.example.data2.storage.model.favorite.FavoriteVacancy
import com.example.domain.model.favorite.FavoriteVacancyDomain
import kotlinx.coroutines.flow.Flow

interface FavoriteStorage {
    fun addToFavorite(favorite: FavoriteVacancyDomain)

    fun deleteFromFavorite(favorite: FavoriteVacancyDomain)

    fun getFavorites(): Flow<List<FavoriteVacancyDomain>>
}