package com.example.data2.storage.favoriteStorage.database

import com.example.data2.database.Favorite
import com.example.data2.database.FavoriteDao
import com.example.data2.database.FavoriteDatabase
import com.example.data2.storage.favoriteStorage.FavoriteStorage
import com.example.data2.storage.model.favorite.FavoriteVacancy
import com.example.domain.model.favorite.FavoriteVacancyDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

 class DatabaseFavoriteStorage @Inject constructor(private val favoriteDao: FavoriteDao): FavoriteStorage {
    override fun addToFavorite(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    override fun deleteFromFavorite(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }

    override fun getFavorites(): Flow<List<Favorite>> {
        return favoriteDao.selectAllFavorites()
    }
}