package com.example.data2.storage.vacancyStorage

import com.example.data2.storage.model.vacancy.Offers

interface VacancyStorage {

    suspend fun getVacancies(): Offers

}