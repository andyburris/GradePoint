package com.andb.apps.aspen.ktor

import com.andb.apps.aspen.response.AspenResponse

interface AspenApi {
    suspend fun request(username: String, password: String): AspenResponse
}