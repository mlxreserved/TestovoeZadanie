package com.example.vacancy.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.AddVacancyToDBUseCase
import com.example.domain.usecase.DeleteVacancyFromDBUseCase
import com.example.domain.usecase.GetCoordinateFromNetworkUseCase
import com.example.domain.usecase.GetFavoritesVacanciesUseCase

class VacancyViewModelFactory(
    private val getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
    private val addVacancyToDBUseCase: AddVacancyToDBUseCase,
    private val deleteVacancyFromDBUseCase: DeleteVacancyFromDBUseCase,
    private val getCoordinateFromNetworkUseCase: GetCoordinateFromNetworkUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VacancyViewModel(
            getFavoritesVacanciesUseCase = getFavoritesVacanciesUseCase,
            addVacancyToDBUseCase = addVacancyToDBUseCase,
            deleteVacancyFromDBUseCase = deleteVacancyFromDBUseCase,
            getCoordinateFromNetworkUseCase = getCoordinateFromNetworkUseCase
        ) as T
    }

}