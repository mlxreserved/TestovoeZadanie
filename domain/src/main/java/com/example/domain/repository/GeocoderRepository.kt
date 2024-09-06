package com.example.domain.repository

import com.example.domain.model.coordinate.CoordinateDomain

interface GeocoderRepository {

    suspend fun getCoordinate(city: String): CoordinateDomain

}