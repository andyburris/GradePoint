package com.andb.apps.aspen.ktor

import com.andb.apps.aspen.response.AspenResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.timeout
import io.ktor.client.request.get
import io.ktor.client.request.header

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
        install(HttpTimeout)
    }

    override suspend fun request(username: String, password: String): AspenResponse {
        return client.get<AspenResponse>("$BASE_URL/course?moreData=true"){
            header("ASPEN_UNAME", username)
            header("ASPEN_PASS", password)
            timeout {
                socketTimeoutMillis = 30 * 1000
            }
        }
    }


    companion object {
        const val DISTRICT_ID = "dcps"
        const val BASE_URL = "https://aspenapi.herokuapp.com/api/v1/$DISTRICT_ID/aspen"
    }

}