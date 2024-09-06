package com.example.data2.storage.geocoderStorage

import com.example.data2.storage.model.coordinate.Coordinate

interface GeocoderStorage {

    suspend fun getCoordinate(city: String): Coordinate

}