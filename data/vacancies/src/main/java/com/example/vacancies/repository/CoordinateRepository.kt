package com.example.vacancies.repository

import com.example.vacancies.api.GeocoderService
import com.example.vacancies.api.model.Coordinate
import javax.inject.Inject


private const val KEY_COORDINATE = "8868527d-80b8-43d6-bab9-f6111ec94ee8"

interface GeocoderRepository{

    suspend fun getCoordinate(city: String): Coordinate

}

class GeocoderRepositoryImpl @Inject internal constructor(private val coordinateService: GeocoderService): GeocoderRepository {

    override suspend fun getCoordinate(city: String): Coordinate = coordinateService.getCoordinate(
        geocode = city,
        apiKey = KEY_COORDINATE
    )
}