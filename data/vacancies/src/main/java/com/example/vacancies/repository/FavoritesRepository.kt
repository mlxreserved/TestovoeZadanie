package com.example.vacancies.repository

import com.example.vacancies.database.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository{

    fun selectAllFavorites(): Flow<List<Favorite>>

    fun delete(favorite: Favorite)

    fun insert(favorite: Favorite)
}