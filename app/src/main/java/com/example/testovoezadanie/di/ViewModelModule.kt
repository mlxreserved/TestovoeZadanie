package com.example.testovoezadanie.di

import androidx.lifecycle.ViewModelProvider
import com.example.favorite.screen.FavoriteViewModelFactory
import com.example.search.screen.SearchViewModelFactory
import com.example.vacancy.screen.VacancyViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideSearchViewModelFactory(searchViewModelFactory: SearchViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun provideVacancyViewModelFactory(vacancyViewModelFactory: VacancyViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun provideFavoriteViewModelFactory(favoriteViewModelFactory: FavoriteViewModelFactory): ViewModelProvider.Factory
}