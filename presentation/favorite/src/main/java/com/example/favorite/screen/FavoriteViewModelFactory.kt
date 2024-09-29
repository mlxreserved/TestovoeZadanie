package com.example.favorite.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.DeleteVacancyFromDBUseCase
import com.example.domain.usecase.GetFavoritesVacanciesUseCase
import com.example.domain.usecase.GetVacanciesFromNetworkUseCase

class FavoriteViewModelFactory(
    private val deleteVacancyFromDBUseCase: DeleteVacancyFromDBUseCase,
    private val getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
    private val getVacanciesFromNetworkUseCase: GetVacanciesFromNetworkUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(
            deleteVacancyFromDBUseCase = deleteVacancyFromDBUseCase,
            getFavoritesVacanciesUseCase = getFavoritesVacanciesUseCase,
            getVacanciesFromNetworkUseCase = getVacanciesFromNetworkUseCase
        ) as T
    }
}