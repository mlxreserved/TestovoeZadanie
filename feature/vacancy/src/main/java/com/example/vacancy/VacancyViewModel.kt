package com.example.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vacancies.api.model.Coordinate
import com.example.vacancies.api.model.vacancy.Offers
import com.example.vacancies.api.model.vacancy.Vacancy
import com.example.vacancies.database.Favorite
import com.example.vacancies.di.MainApp
import com.example.vacancies.repository.FavoritesRepository
import com.example.vacancies.repository.GeocoderRepository
import com.example.vacancies.repository.GeocoderRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class VacancyState(
    val vacancy: Vacancy? = null
)

internal data class DatabaseState(
    val favorites: List<Favorite> = listOf()
)

internal data class CoordinateState(
    val coordinate: Result = Result.Loading
)


internal class VacancyViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val geocoderRepository: GeocoderRepository
): ViewModel() {

    private val _vacancyState = MutableStateFlow(VacancyState())
    val vacancyState = _vacancyState.asStateFlow()

    private val _coordinateState = MutableStateFlow(CoordinateState())
    val coordinateState = _coordinateState.asStateFlow()

    val databaseState: StateFlow<DatabaseState> = favoritesRepository.selectAllFavorites().map {DatabaseState(it)}
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DatabaseState()
        )

    fun setVacancy(vacancy: Vacancy){
        _vacancyState.update { it.copy(vacancy = vacancy) }
    }

    fun getCoordinates(city: String){
        viewModelScope.launch {
            _coordinateState.update { it.copy(coordinate = Result.Loading) }
            val res = try {
                val coordinates = geocoderRepository.getCoordinate(city)
                val pos = coordinates.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos.replace(" ", ",")
                coordinates.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos = pos
                Result.Success(coordinates)
            } catch (e: Exception){
                Result.Error(e.message.toString())
            }
            _coordinateState.update { it.copy(coordinate = res) }
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
                val favoritesRepository = application.databaseComponent.favoritesRepository
                val geocoderRepository = application.networkComponent.geocoderRepository
                VacancyViewModel(
                    favoritesRepository = favoritesRepository,
                    geocoderRepository = geocoderRepository
                )
            }
        }
    }

}

internal sealed interface Result{
    data class Success(val data: Coordinate): Result
    object Loading: Result
    data class Error(val errorMessage: String): Result
}