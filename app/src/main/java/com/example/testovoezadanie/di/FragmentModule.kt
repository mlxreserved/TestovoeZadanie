package com.example.testovoezadanie.di

import com.example.favorite.screen.FavoriteFragment
import com.example.search.screen.MoreFragment
import com.example.search.screen.SearchFragment
import com.example.vacancy.screen.VacancyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeMoreFragment(): MoreFragment

    @ContributesAndroidInjector
    abstract fun contributeVacancyFragment(): VacancyFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteFragment(): FavoriteFragment
}
