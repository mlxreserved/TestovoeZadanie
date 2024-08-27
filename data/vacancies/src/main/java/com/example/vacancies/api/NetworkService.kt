package com.example.vacancies.api

import com.example.vacancies.api.model.Coordinate
import com.example.vacancies.api.model.vacancy.Offers
import retrofit2.http.GET
import retrofit2.http.Query


internal interface NetworkService {
    @GET("u/0/uc")
    suspend fun getVacancies( @Query("id") id: String,
                              @Query("export") export: String): Offers
}

internal interface GeocoderService{
    @GET("1.x/")
    suspend fun getCoordinate(
        @Query("geocode") geocode: String,
        @Query("apikey") apiKey: String,
        @Query("lang") lang: String = "ru-RU",
        @Query("format") format: String = "json"
    ): Coordinate
}