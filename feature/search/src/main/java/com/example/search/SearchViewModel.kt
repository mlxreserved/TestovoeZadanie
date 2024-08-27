package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vacancies.api.model.vacancy.Offers
import com.example.vacancies.database.Favorite
import com.example.vacancies.di.MainApp
import com.example.vacancies.repository.FavoritesRepository
import com.example.vacancies.repository.NetworkRepository
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
    val favorites: List<Favorite> = listOf()
)

internal class SearchViewModel(
    private val networkRepository: NetworkRepository,
    private val favoritesRepository: FavoritesRepository
): ViewModel() {



    private val _networkState = MutableStateFlow(NetworkState())
    val networkState: StateFlow<NetworkState> = _networkState.asStateFlow()

    val databaseState: StateFlow<DatabaseState> = favoritesRepository.selectAllFavorites().map {DatabaseState(it)}
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DatabaseState()
        )

    init{
        getVacancies()
    }

    fun getVacancies(){
        viewModelScope.launch {
            _networkState.update { it.copy(result = Result.Loading) }
            val res = try {
                val vacancies = networkRepository.getVacancies()
                Result.Success(vacancies)
            } catch (e: Exception){
                Result.Error(e.message.toString())
            }
            _networkState.update { it.copy(result = res) }
        }
    }

    fun addToDB(item: String){
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.insert(Favorite(item))
        }
    }

    fun deleteFromDB(item: String){
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.delete(Favorite(item))
        }
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L


        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApp)
                val networkRepository = application.networkComponent.networkRepository
                val favoritesRepository = application.databaseComponent.favoritesRepository
                SearchViewModel(
                    networkRepository = networkRepository,
                    favoritesRepository = favoritesRepository
                )
            }
        }
    }
}

internal sealed interface Result{
    data class Success(val data: Offers): Result
    object Loading: Result
    data class Error(val errorMessage: String): Result
}