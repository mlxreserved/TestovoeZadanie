package com.example.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.data2.di.MainApp
import com.example.data2.repository.GeocoderRepositoryImpl
import com.example.data2.storage.favoriteStorage.database.DatabaseFavoriteStorage
import com.example.data2.storage.geocoderStorage.network.NetworkGeocoderStorage
import com.example.data2.storage.model.vacancy.Vacancy
import com.example.domain.model.coordinate.CoordinateDomain
import com.example.domain.model.favorite.FavoriteVacancyDomain
import com.example.domain.repository.FavoriteRepository
import com.example.domain.usecase.AddVacancyToDBUseCase
import com.example.domain.usecase.DeleteVacancyFromFavoriteUseCase
import com.example.domain.usecase.GetCoordinateFromNetworkUseCase
import com.example.domain.usecase.GetFavoritesVacanciesUseCase
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
    val favorites: List<FavoriteVacancyDomain> = listOf()
)

internal data class CoordinateState(
    val coordinate: Result = Result.Loading
)


internal class VacancyViewModel(
    //private val favoritesRepository: FavoriteRepository,
    //private val geocoderRepository: GeocoderRepository
    private val getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
    private val addVacancyToDBUseCase: AddVacancyToDBUseCase,
    private val deleteVacancyFromFavoriteUseCase: DeleteVacancyFromFavoriteUseCase,
    private val getCoordinateFromNetworkUseCase: GetCoordinateFromNetworkUseCase
): ViewModel() {


    private val _vacancyState = MutableStateFlow(VacancyState())
    val vacancyState = _vacancyState.asStateFlow()

    private val _coordinateState = MutableStateFlow(CoordinateState())
    val coordinateState = _coordinateState.asStateFlow()

    val databaseState: StateFlow<DatabaseState> = getFavoritesVacanciesUseCase.execute().map { DatabaseState(it) }
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
                val coordinates = getCoordinateFromNetworkUseCase.execute(city)
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
            addVacancyToDBUseCase.execute(FavoriteVacancyDomain(item))
        }
    }

    fun deleteFromDB(item: String){
        viewModelScope.launch(Dispatchers.IO) {
            deleteVacancyFromFavoriteUseCase.execute(FavoriteVacancyDomain(item))
        }
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L


        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as MainApp
                val favoritesRepository = application.databaseComponent.favoritesRepository
                val addVacancyToDBUseCase = AddVacancyToDBUseCase(favoritesRepository)
                val deleteVacancyFromFavoriteUseCase = DeleteVacancyFromFavoriteUseCase(favoritesRepository)
                val getFavoritesVacanciesUseCase = GetFavoritesVacanciesUseCase(favoritesRepository)
                val geocoderRepository = application.networkComponent.geocoderRepository
                VacancyViewModel(
                    addVacancyToDBUseCase = addVacancyToDBUseCase,
                    getFavoritesVacanciesUseCase = getFavoritesVacanciesUseCase,
                    deleteVacancyFromFavoriteUseCase = deleteVacancyFromFavoriteUseCase,
                    getCoordinateFromNetworkUseCase = GetCoordinateFromNetworkUseCase(geocoderRepository)

                )
            }
        }
    }

}

internal sealed interface Result{
    data class Success(val data: CoordinateDomain): Result
    object Loading: Result
    data class Error(val errorMessage: String): Result
}