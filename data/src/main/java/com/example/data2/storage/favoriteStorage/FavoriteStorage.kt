package com.example.data2.storage.favoriteStorage

import com.example.data2.database.Favorite
import com.example.data2.storage.model.favorite.FavoriteVacancy
import com.example.domain.model.favorite.FavoriteVacancyDomain
import kotlinx.coroutines.flow.Flow

interface FavoriteStorage {
    fun addToFavorite(favorite: Favorite)

    fun deleteFromFavorite(favorite: Favorite)

    fun getFavorites(): Flow<List<Favorite>>
}