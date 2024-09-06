package com.example.data2.storage.geocoderStorage

import com.example.domain.model.coordinate.CoordinateDomain

interface GeocoderStorage {

    suspend fun getCoordinate(city: String): CoordinateDomain

}