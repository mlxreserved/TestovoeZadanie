package com.example.testovoezadanie.di

import android.app.Application
import android.content.Context
import com.example.data2.database.FavoriteDao
import com.example.data2.database.FavoriteDatabase
import com.example.data2.repository.FavoriteRepositoryImpl
import com.example.data2.repository.GeocoderRepositoryImpl
import com.example.data2.repository.VacancyRepositoryImpl
import com.example.data2.storage.favoriteStorage.FavoriteStorage
import com.example.data2.storage.favoriteStorage.database.DatabaseFavoriteStorage
import com.example.data2.storage.geocoderStorage.GeocoderStorage
import com.example.data2.storage.geocoderStorage.network.NetworkGeocoderStorage
import com.example.data2.storage.services.GeocoderService
import com.example.data2.storage.services.NetworkService
import com.example.data2.storage.vacancyStorage.VacancyStorage
import com.example.data2.storage.vacancyStorage.network.NetworkVacancyStorage
import com.example.domain.repository.FavoriteRepository
import com.example.domain.repository.GeocoderRepository
import com.example.domain.repository.VacancyRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


private const val BASE_URL = "https://drive.usercontent.google.com/"
private const val BASE_URL_COORDINATE = "https://geocode-maps.yandex.ru/"

@Module(includes = [FavoriteModule::class, GeocoderModule::class, VacancyModule::class])
class DataModule {}


@Module
class FavoriteModule {

    @Singleton
    @Provides
    fun provideFavoriteDatabase(context: Context): FavoriteDatabase {
        return FavoriteDatabase.getDatabase(context)
    }

    @Provides
    fun provideFavoriteDao(db: FavoriteDatabase): FavoriteDao {
        return db.favoriteDao()
    }

    @Provides
    fun provideFavoriteStorage(favoriteDao: FavoriteDao): FavoriteStorage {
        return DatabaseFavoriteStorage(favoriteDao = favoriteDao)
    }

    @Provides
    fun provideFavoriteRepository(favoriteStorage: FavoriteStorage): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteStorage = favoriteStorage)
    }

}

@Module
class GeocoderModule {

    @Provides
    fun provideGeocoderService(): GeocoderService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_COORDINATE)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
        return retrofit.create()
    }

    @Provides
    fun provideGeocoderStorage(geocoderService: GeocoderService): GeocoderStorage {
        return NetworkGeocoderStorage(geocoderService = geocoderService)
    }

    @Provides
    fun provideGeocoderRepository(geocoderStorage: GeocoderStorage): GeocoderRepository {
        return GeocoderRepositoryImpl(geocoderStorage = geocoderStorage)
    }

}

@Module
class VacancyModule {

    @Provides
    fun provideNetworkService(): NetworkService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create()
    }

    @Provides
    fun provideVacancyStorage(networkService: NetworkService): VacancyStorage {
        return NetworkVacancyStorage(networkService = networkService)
    }

    @Provides
    fun provideVacancyRepository(vacancyStorage: VacancyStorage): VacancyRepository {
        return VacancyRepositoryImpl(vacancyStorage = vacancyStorage)
    }

}