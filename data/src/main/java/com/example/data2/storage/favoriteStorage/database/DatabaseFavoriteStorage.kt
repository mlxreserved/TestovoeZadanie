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
    override fun addToFavorite(favorite: FavoriteVacancyDomain) {
        favoriteDao.insert(mapFavoriteVacancyDomainToFavorite(favorite))
    }

    override fun deleteFromFavorite(favorite: FavoriteVacancyDomain) {
        favoriteDao.delete(mapFavoriteVacancyDomainToFavorite(favorite))
    }

    override fun getFavorites(): Flow<List<FavoriteVacancyDomain>> {
        return favoriteDao.selectAllFavorites().map{
            it.map{ favorite ->
                mapFavoriteToFavoriteVacancyDomain(favorite)
            }
        }
    }

    fun mapFavoriteToFavoriteVacancyDomain(favorite: Favorite): FavoriteVacancyDomain{
        return FavoriteVacancyDomain(id = favorite.id)
    }

    fun mapFavoriteVacancyDomainToFavorite(favoriteVacancyDomain: FavoriteVacancyDomain): Favorite{
        return Favorite(id = favoriteVacancyDomain.id)
    }
}