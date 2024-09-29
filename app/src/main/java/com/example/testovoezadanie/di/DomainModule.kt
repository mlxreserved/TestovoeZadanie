package com.example.testovoezadanie.di

import com.example.domain.repository.FavoriteRepository
import com.example.domain.repository.GeocoderRepository
import com.example.domain.repository.VacancyRepository
import com.example.domain.usecase.AddVacancyToDBUseCase
import com.example.domain.usecase.DeleteVacancyFromDBUseCase
import com.example.domain.usecase.GetCoordinateFromNetworkUseCase
import com.example.domain.usecase.GetFavoritesVacanciesUseCase
import com.example.domain.usecase.GetVacanciesFromNetworkUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideAddVacancyToDBUseCase(favoriteRepository: FavoriteRepository): AddVacancyToDBUseCase {
        return AddVacancyToDBUseCase(favoriteRepository = favoriteRepository)
    }

    @Provides
    fun provideDeleteVacancyFromDBUseCase(favoriteRepository: FavoriteRepository): DeleteVacancyFromDBUseCase {
        return DeleteVacancyFromDBUseCase(favoriteRepository = favoriteRepository)
    }

    @Provides
    fun provideGetCoordinateFromNetworkUseCase(geocoderRepository: GeocoderRepository): GetCoordinateFromNetworkUseCase {
        return GetCoordinateFromNetworkUseCase(geocoderRepository = geocoderRepository)
    }

    @Provides
    fun provideGetFavoritesVacanciesUseCase(favoriteRepository: FavoriteRepository): GetFavoritesVacanciesUseCase {
        return GetFavoritesVacanciesUseCase(favoriteRepository = favoriteRepository)
    }

    @Provides
    fun provideGetVacanciesFromNetworkUseCase(vacancyRepository: VacancyRepository): GetVacanciesFromNetworkUseCase {
        return GetVacanciesFromNetworkUseCase(vacancyRepository = vacancyRepository)
    }
}