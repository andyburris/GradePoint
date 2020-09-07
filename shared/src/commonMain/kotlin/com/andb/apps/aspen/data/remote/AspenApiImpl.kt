package com.andb.apps.aspen.data.remote

import co.touchlab.stately.ensureNeverFrozen
import com.andb.apps.aspen.response.CheckLoginResponse
import com.andb.apps.aspen.response.CourseListResponse
import com.andb.apps.aspen.response.RecentResponse
import com.andb.apps.aspen.util.result.SuspendableResult
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
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

    init {
        ensureNeverFrozen()
    }

    override suspend fun checkLogin(username: String, password: String): SuspendableResult<CheckLoginResponse, Exception> =
        SuspendableResult.of {
            client.get<CheckLoginResponse>("$BASE_URL/checkLogin") {
                header("ASPEN_UNAME", username)
                header("ASPEN_PASS", password)
                timeout {
                    socketTimeoutMillis = 30 * 1000
                }
            }
        }

    override suspend fun getSubjects(username: String, password: String, term: Int?): SuspendableResult<CourseListResponse, Exception> {
        val response: SuspendableResult<CourseListResponse, Exception> = SuspendableResult.of {
            val termParam = if (term != null) "&term=$term" else ""
            return@of client.get<CourseListResponse>("$BASE_URL/course?moreData=true$termParam") {
                header("ASPEN_UNAME", username)
                header("ASPEN_PASS", password)
                timeout {
                    socketTimeoutMillis = 30 * 1000
                }
            }
        }
        println("response = $response")
        return response
    }

    override suspend fun getRecentAssignments(username: String, password: String): SuspendableResult<RecentResponse, Exception> =
        SuspendableResult.of {
            client.get<RecentResponse>("$BASE_URL/recent") {
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