package com.example.testovoezadanie.di

import android.app.Application
import android.content.Context
import com.example.domain.usecase.AddVacancyToDBUseCase
import com.example.domain.usecase.DeleteVacancyFromDBUseCase
import com.example.domain.usecase.GetCoordinateFromNetworkUseCase
import com.example.domain.usecase.GetFavoritesVacanciesUseCase
import com.example.domain.usecase.GetVacanciesFromNetworkUseCase
import com.example.favorite.screen.FavoriteViewModelFactory
import com.example.search.screen.SearchViewModelFactory
import com.example.vacancy.screen.VacancyViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideFavoriteViewModelFactory(
        deleteVacancyFromDBUseCase: DeleteVacancyFromDBUseCase,
        getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
        getVacanciesFromNetworkUseCase: GetVacanciesFromNetworkUseCase
    ): FavoriteViewModelFactory {
        return FavoriteViewModelFactory(
            deleteVacancyFromDBUseCase = deleteVacancyFromDBUseCase,
            getFavoritesVacanciesUseCase = getFavoritesVacanciesUseCase,
            getVacanciesFromNetworkUseCase = getVacanciesFromNetworkUseCase
        )
    }

    @Provides
    fun provideSearchViewModelFactory(
        addVacancyToDBUseCase: AddVacancyToDBUseCase,
        deleteVacancyFromDBUseCase: DeleteVacancyFromDBUseCase,
        getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
        getVacanciesFromNetworkUseCase: GetVacanciesFromNetworkUseCase
    ): SearchViewModelFactory {
        return SearchViewModelFactory(
            addVacancyToDBUseCase = addVacancyToDBUseCase,
            deleteVacancyFromDBUseCase = deleteVacancyFromDBUseCase,
            getFavoritesVacanciesUseCase = getFavoritesVacanciesUseCase,
            getVacanciesFromNetworkUseCase = getVacanciesFromNetworkUseCase
        )
    }

    @Provides
    fun provideVacancyViewModelFactory(
        addVacancyToDBUseCase: AddVacancyToDBUseCase,
        deleteVacancyFromDBUseCase: DeleteVacancyFromDBUseCase,
        getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
        getCoordinateFromNetworkUseCase: GetCoordinateFromNetworkUseCase
    ): VacancyViewModelFactory {
        return VacancyViewModelFactory(
            addVacancyToDBUseCase = addVacancyToDBUseCase,
            getFavoritesVacanciesUseCase = getFavoritesVacanciesUseCase,
            getCoordinateFromNetworkUseCase = getCoordinateFromNetworkUseCase,
            deleteVacancyFromDBUseCase = deleteVacancyFromDBUseCase
        )
    }


}