package com.andb.apps.aspen.ktor

import com.andb.apps.aspen.response.AspenResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get

class AspenApiImpl : AspenApi {

    private val cookiesStorage = AcceptAllCookiesStorage()
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    override suspend fun request(username: String, password: String): AspenResponse {
        return client.get<AspenResponse>("$BASE_URL/course")
    }


    companion object {
        const val DISTRICT_ID = "dcps"
        const val BASE_URL = "https://aspencheck.herokuapp.com/api/v1/$DISTRICT_ID/aspen"
    }

}