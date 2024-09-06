package com.example.data2.storage.vacancyStorage.network

import com.example.data2.storage.model.vacancy.Offers
import com.example.data2.storage.services.NetworkService
import com.example.data2.storage.vacancyStorage.VacancyStorage
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

private const val ID = "1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r"

class NetworkVacancyStorage @Inject constructor(private val networkService: NetworkService): VacancyStorage {

    override suspend fun getVacancies(): Offers =
                networkService.getVacancies(id = ID, export = "download")

}