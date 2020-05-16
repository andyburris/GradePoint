package com.andb.apps.aspen.ktor

import com.andb.apps.aspen.response.BreedResult

interface DogApi {
    suspend fun getJsonFromApi(): BreedResult
}