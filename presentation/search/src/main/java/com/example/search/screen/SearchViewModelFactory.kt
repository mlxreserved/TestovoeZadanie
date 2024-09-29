package com.example.search.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.AddVacancyToDBUseCase
import com.example.domain.usecase.DeleteVacancyFromDBUseCase
import com.example.domain.usecase.GetFavoritesVacanciesUseCase
import com.example.domain.usecase.GetVacanciesFromNetworkUseCase

class SearchViewModelFactory(
    private val addVacancyToDBUseCase: AddVacancyToDBUseCase,
    private val deleteVacancyFromDBUseCase: DeleteVacancyFromDBUseCase,
    private val getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
    private val getVacanciesFromNetworkUseCase: GetVacanciesFromNetworkUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            addVacancyToDBUseCase = addVacancyToDBUseCase,
            deleteVacancyFromDBUseCase = deleteVacancyFromDBUseCase,
            getFavoritesVacanciesUseCase = getFavoritesVacanciesUseCase,
            getVacanciesFromNetworkUseCase = getVacanciesFromNetworkUseCase
        ) as T
    }

}