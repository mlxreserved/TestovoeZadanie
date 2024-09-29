package com.example.search.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.domain.model.favorite.FavoriteVacancyDomain
import com.example.domain.model.vacancy.OffersDomain
import com.example.domain.usecase.AddVacancyToDBUseCase
import com.example.domain.usecase.DeleteVacancyFromDBUseCase
import com.example.domain.usecase.GetFavoritesVacanciesUseCase
import com.example.domain.usecase.GetVacanciesFromNetworkUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal data class NetworkState(
    val result: Result = Result.Loading
)

internal data class DatabaseState(
    val favorites: List<FavoriteVacancyDomain> = listOf()
)

internal class SearchViewModel(
    private val addVacancyToDBUseCase: AddVacancyToDBUseCase,
    private val deleteVacancyFromDBUseCase: DeleteVacancyFromDBUseCase,
    private val getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
    private val getVacanciesFromNetworkUseCase: GetVacanciesFromNetworkUseCase
) : ViewModel() {


    private val _networkState = MutableStateFlow(NetworkState())
    val networkState: StateFlow<NetworkState> = _networkState.asStateFlow()

    val databaseState: StateFlow<DatabaseState> =
        getFavoritesVacanciesUseCase.execute().map { DatabaseState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DatabaseState()
            )

    init {
        getVacancies()
    }

    fun getVacancies() {
        viewModelScope.launch {
            _networkState.update { it.copy(result = Result.Loading) }
            val res = try {
                val vacancies = getVacanciesFromNetworkUseCase.execute()
                Result.Success(vacancies)
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
            _networkState.update { it.copy(result = res) }
        }
    }

    fun addToDB(item: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addVacancyToDBUseCase.execute(FavoriteVacancyDomain(item))
        }
    }

    fun deleteFromDB(item: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteVacancyFromDBUseCase.execute(FavoriteVacancyDomain(item))
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L


        /*val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApp)
                val favoritesRepository = application.databaseComponent.favoritesRepository
                val vacancyRepository = application.networkComponent.vacancyRepository
                SearchViewModel(
                    addVacancyToDBUseCase = AddVacancyToDBUseCase(favoritesRepository),
                    deleteVacancyFromDBUseCase = DeleteVacancyFromDBUseCase(favoritesRepository),
                    getFavoritesVacanciesUseCase = GetFavoritesVacanciesUseCase(favoritesRepository),
                    getVacanciesFromNetworkUseCase = GetVacanciesFromNetworkUseCase(vacancyRepository)
                )
            }
        }*/
    }
}

internal sealed interface Result {
    data class Success(val data: OffersDomain) : Result
    object Loading : Result
    data class Error(val errorMessage: String) : Result
}