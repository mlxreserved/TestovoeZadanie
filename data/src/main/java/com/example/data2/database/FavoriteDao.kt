package com.example.data2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.domain.model.favorite.FavoriteVacancyDomain
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {

    @Insert
    fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun selectAllFavorites(): Flow<List<Favorite>>

    @Delete
    fun delete(favorite: Favorite)

}