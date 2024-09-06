package com.example.vacancies.di

import android.content.Context
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/*
@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent{
    val favoritesRepository: FavoritesRepository
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
}

@Module
internal interface DatabaseBindModule{
    @Binds
    fun bindOfflineFavoritesRepository_to_FavoritesRepository(offlineFavoritesRepository: OfflineFavoritesRepository): FavoritesRepository
}*/
