package com.example.data2.storage.geocoderStorage.network

import com.example.data2.storage.geocoderStorage.GeocoderStorage
import com.example.data2.storage.model.coordinate.Coordinate
import com.example.data2.storage.services.GeocoderService
import com.example.domain.model.coordinate.CoordinateDomain
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject



class NetworkGeocoderStorage @Inject constructor(private val geocoderService: GeocoderService): GeocoderStorage {

    override suspend fun getCoordinate(city: String): Coordinate = geocoderService.getCoordinate(city)
}