package com.example.data2.di

import com.example.data2.repository.GeocoderRepositoryImpl
import com.example.data2.repository.VacancyRepositoryImpl
import com.example.data2.storage.geocoderStorage.GeocoderStorage
import com.example.data2.storage.geocoderStorage.network.NetworkGeocoderStorage
import com.example.data2.storage.model.vacancy.Vacancy
import com.example.data2.storage.services.GeocoderService
import com.example.data2.storage.services.NetworkService
import com.example.data2.storage.vacancyStorage.VacancyStorage
import com.example.data2.storage.vacancyStorage.network.NetworkVacancyStorage
import com.example.domain.repository.GeocoderRepository
import com.example.domain.repository.VacancyRepository
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
    val vacancyRepository: VacancyRepository
    val geocoderRepository: GeocoderRepository
}

@Module(includes = [NetworkModule::class, AppBindModule::class])
internal class AppModule

@Module
internal class NetworkModule{

    @Provides
    fun provideNetworkService(): NetworkService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create()
    }

    @Provides
    fun provideGeocoderService(): GeocoderService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_COORDINATE)
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
            .build()
        return retrofit.create()
    }

    @Provides
    fun provideVacancyStorage(networkService: NetworkService): VacancyStorage {
        return NetworkVacancyStorage(networkService)
    }

    @Provides
    fun provideGeocoderStorage(geocoderService: GeocoderService): GeocoderStorage {
        return NetworkGeocoderStorage(geocoderService)
    }

}

@Module
internal interface AppBindModule{
    @Binds
    fun bindVacancyRepositoryImpl_to_VacancyRepository(
        vacancyRepositoryImpl: VacancyRepositoryImpl
    ): VacancyRepository

    @Binds
    fun bindGeocoderRepositoryImpl_toGeocoderRepository(
        geoocoderRepositoryImpl: GeocoderRepositoryImpl
    ): GeocoderRepository
}