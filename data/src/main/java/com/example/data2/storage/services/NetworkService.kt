package com.example.data2.storage.services

import com.example.data2.storage.model.coordinate.Coordinate
import com.example.data2.storage.model.vacancy.Offers
import com.example.domain.model.coordinate.CoordinateDomain
import retrofit2.http.GET
import retrofit2.http.Query
private const val ID = "1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r"
private const val KEY_COORDINATE = "8868527d-80b8-43d6-bab9-f6111ec94ee8"

interface NetworkService {
    @GET("u/0/uc")
    suspend fun getVacancies(@Query("id") id: String = ID,
                             @Query("export") export: String = "download"): Offers


}

interface GeocoderService {
    @GET("1.x/")
    suspend fun getCoordinate(
        @Query("geocode") geocode: String,
        @Query("apikey") apiKey: String = KEY_COORDINATE,
        @Query("lang") lang: String = "ru-RU",
        @Query("format") format: String = "json"
    ): CoordinateDomain
}