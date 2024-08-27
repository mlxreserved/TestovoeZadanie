package com.example.vacancies.di

import com.example.vacancies.api.GeocoderService
import com.example.vacancies.api.NetworkService
import com.example.vacancies.repository.GeocoderRepository
import com.example.vacancies.repository.GeocoderRepositoryImpl
import com.example.vacancies.repository.NetworkRepository
import com.example.vacancies.repository.NetworkRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

private const val BASE_URL = "https://drive.usercontent.google.com/"
private const val BASE_URL_COORDINATE = "https://geocode-maps.yandex.ru/"

@Singleton
@Component(modules = [AppModule::class])
interface NetworkComponent{
    val networkRepository: NetworkRepository
    val geocoderRepository: GeocoderRepository
}

@Module(includes = [NetworkModule::class, AppBindModule::class])
internal class AppModule

@Module
internal class NetworkModule{

    @Provides
    fun provideNetworkService(): NetworkService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create()
    }

    @Provides
    fun provideGeocoderService(): GeocoderService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_COORDINATE)
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
            .build()
        return retrofit.create()
    }

}

@Module
internal interface AppBindModule{
    @Binds
    fun bindNetworkRepositoryImpl_to_NetworkRepository(
        networkRepositoryImpl: NetworkRepositoryImpl
    ): NetworkRepository

    @Binds
    fun bindGeocoderRepositoryImpl_toGeocoderRepository(
        geoocoderRepositoryImpl: GeocoderRepositoryImpl
    ): GeocoderRepository
}