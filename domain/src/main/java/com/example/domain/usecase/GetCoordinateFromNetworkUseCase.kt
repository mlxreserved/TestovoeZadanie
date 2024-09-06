package com.example.domain.usecase

import com.example.domain.model.coordinate.CoordinateDomain
import com.example.domain.repository.GeocoderRepository

class GetCoordinateFromNetworkUseCase(
    private val geocoderRepository: GeocoderRepository
) {

    suspend fun execute(city: String): CoordinateDomain = geocoderRepository.getCoordinate(city)

}