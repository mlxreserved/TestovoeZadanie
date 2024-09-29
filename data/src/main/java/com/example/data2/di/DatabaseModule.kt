/*

package com.example.data2.di

import android.content.Context
import androidx.room.Database
import com.example.data2.database.Favorite
import com.example.data2.database.FavoriteDao
import com.example.data2.database.FavoriteDatabase
import com.example.data2.repository.FavoriteRepositoryImpl
import com.example.data2.storage.favoriteStorage.FavoriteStorage
import com.example.data2.storage.favoriteStorage.database.DatabaseFavoriteStorage
import com.example.data2.storage.model.favorite.FavoriteVacancy
import com.example.domain.repository.FavoriteRepository
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent{
    val favoritesRepository: FavoriteRepository
}

@Module(includes = [DatabaseBindModule::class])
internal class DatabaseModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideFavoriteDatabase(): FavoriteDatabase{
        return FavoriteDatabase.getDatabase(context)
    }

    @Provides
    fun provideFavoriteDao(db: FavoriteDatabase): FavoriteDao{
        return db.favoriteDao()
    }

    @Provides
    fun provideFavoriteStorage(dao: FavoriteDao): FavoriteStorage{
        return DatabaseFavoriteStorage(dao)
    }

}

@Module
internal interface DatabaseBindModule{
    @Binds
    fun bindFavoriteRepositoryImpl_to_FavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository
}
*/
