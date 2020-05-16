package com.andb.apps.aspen

import androidx.compose.Model
import com.andb.apps.aspen.android.BuildConfig
import com.andb.apps.aspen.ktor.AspenApi
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.util.newIOThread
import io.ktor.client.features.ClientRequestException
import org.koin.core.KoinComponent
import org.koin.core.inject

@Model
object AndroidAppState : KoinComponent {
    private val aspenApi: AspenApi by inject()

    var screen = AppState.screen
    fun login(username: String, password: String){
        newIOThread {
            try{
                aspenApi.request(BuildConfig.TEST_USERNAME, BuildConfig.TEST_PASSWORD)
            }catch (e: ClientRequestException){
                e.printStackTrace()
            }
        }
    }
}