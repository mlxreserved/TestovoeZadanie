package com.example.data2.repository

import com.example.data2.database.Favorite
import com.example.data2.database.FavoriteDao
import com.example.data2.storage.favoriteStorage.FavoriteStorage
import com.example.data2.storage.model.favorite.FavoriteVacancy
import com.example.domain.model.favorite.FavoriteVacancyDomain
import com.example.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val favoriteStorage: FavoriteStorage): FavoriteRepository {
    override fun addToFavorite(favorite: FavoriteVacancyDomain) {
        favoriteStorage.addToFavorite(mapFavoriteVacancyDomainToFavorite(favorite))
    }


    override fun deleteFromFavorite(favorite: FavoriteVacancyDomain) {
        favoriteStorage.deleteFromFavorite(mapFavoriteVacancyDomainToFavorite(favorite))
    }


    override fun getFavorites(): Flow<List<FavoriteVacancyDomain>> {
        val res = favoriteStorage.getFavorites().map {
            it.map { favorite ->
                mapFavoriteToFavoriteVacancyDomain(favorite)
            }
        }
        return res
    }

    private fun mapFavoriteToFavoriteVacancyDomain(favorite: Favorite): FavoriteVacancyDomain{
        return FavoriteVacancyDomain(id = favorite.id)
    }

    private fun mapFavoriteVacancyDomainToFavorite(favoriteVacancyDomain: FavoriteVacancyDomain): Favorite{
        return Favorite(id = favoriteVacancyDomain.id)
    }

}