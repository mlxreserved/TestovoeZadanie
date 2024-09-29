package com.example.vacancy.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.coordinate.CoordinateDomain
import com.example.domain.model.favorite.FavoriteVacancyDomain
import com.example.domain.model.vacancy.VacancyDomain
import com.example.domain.usecase.AddVacancyToDBUseCase
import com.example.domain.usecase.DeleteVacancyFromDBUseCase
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
    val vacancy: VacancyDomain? = null
)

internal data class DatabaseState(
    val favorites: List<FavoriteVacancyDomain> = listOf()
)

internal data class CoordinateState(
    val coordinate: Result = Result.Loading
)


internal class VacancyViewModel(
    private val getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
    private val addVacancyToDBUseCase: AddVacancyToDBUseCase,
    private val deleteVacancyFromDBUseCase: DeleteVacancyFromDBUseCase,
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

    fun setVacancy(vacancy: VacancyDomain){
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
            deleteVacancyFromDBUseCase.execute(FavoriteVacancyDomain(item))
        }
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L


        /*val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as MainApp
                val favoritesRepository = application.databaseComponent.favoritesRepository
                val addVacancyToDBUseCase = AddVacancyToDBUseCase(favoritesRepository)
                val deleteVacancyFromFavoriteUseCase = DeleteVacancyFromDBUseCase(favoritesRepository)
                val getFavoritesVacanciesUseCase = GetFavoritesVacanciesUseCase(favoritesRepository)
                val geocoderRepository = application.networkComponent.geocoderRepository
                VacancyViewModel(
                    addVacancyToDBUseCase = addVacancyToDBUseCase,
                    getFavoritesVacanciesUseCase = getFavoritesVacanciesUseCase,
                    deleteVacancyFromFavoriteUseCase = deleteVacancyFromFavoriteUseCase,
                    getCoordinateFromNetworkUseCase = GetCoordinateFromNetworkUseCase(geocoderRepository)

                )
            }
        }*/
    }

}

internal sealed interface Result{
    data class Success(val data: CoordinateDomain): Result
    object Loading: Result
    data class Error(val errorMessage: String): Result
}