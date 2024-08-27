package com.example.vacancies.repository

import com.example.vacancies.api.NetworkService
import com.example.vacancies.api.model.vacancy.Offers
import javax.inject.Inject

private const val ID = "1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r"

interface NetworkRepository{
    suspend fun getVacancies(): Offers
}

class NetworkRepositoryImpl @Inject internal constructor(private val networkService: NetworkService):
    NetworkRepository {
    override suspend fun getVacancies(): Offers =
        networkService.getVacancies(id= ID, export = "download")

}