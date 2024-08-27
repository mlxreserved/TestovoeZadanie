package com.example.vacancies.repository

import com.example.vacancies.database.Favorite
import com.example.vacancies.database.FavoriteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


internal class OfflineFavoritesRepository @Inject constructor(private val favoriteDao: FavoriteDao): FavoritesRepository {
    override fun selectAllFavorites(): Flow<List<Favorite>> = favoriteDao.selectAllFavorites()

    override fun delete(favorite: Favorite) = favoriteDao.delete(favorite)

    override fun insert(favorite: Favorite) = favoriteDao.insert(favorite)


}