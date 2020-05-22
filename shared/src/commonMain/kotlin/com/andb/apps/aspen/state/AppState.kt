package com.andb.apps.aspen.state

import com.andb.apps.aspen.ktor.AspenApi
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.printThrowable
import com.andb.apps.aspen.response.AspenResponse
import com.andb.apps.aspen.response.toSubjectList
import com.andb.apps.aspen.util.newIOThread
import com.netguru.kissme.Kissme
import io.ktor.client.features.ClientRequestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject

object AppState : KoinComponent {
    private val aspenApi: AspenApi by inject()
    private val storage: Kissme by inject()

    private var screens: MutableStateFlow<List<Screen>> = MutableStateFlow(
        when(loggedIn()){
            true -> {
                login(storage.getString("username", "")!!, storage.getString("password", "")!!)
                listOf(Screen.Subjects.Loading)
            }
            false -> listOf(Screen.Login)
        }
    )
    val currentScreen get() = screens.map { it.last() }

    fun openSubject(subject: Subject){
        screens.value += Screen.Assignments(subject)
    }

    fun goBack(): Boolean {
        println("goBack - screen = $screens")
        return when {
            screens.value.isNotEmpty() -> {
                screens.value = screens.value.dropLast(1)
                true
            }
            else -> false
        }
    }

    private fun loggedIn(): Boolean = storage.contains("username") && storage.contains("password")

    fun login(username: String, password: String) {
        newIOThread {
            try {
                val response: AspenResponse = aspenApi.request(username, password)
                if (response.errors.title == null) {
                    storage.putString("username", username)
                    storage.putString("password", password)
                }
                screens.value = screens.value.filter { it !is Screen.Subjects.Loading } + Screen.Subjects.List(response.toSubjectList())
            } catch (e: ClientRequestException) {
                printThrowable(e)
            }
        }
    }

    fun logout() {
        storage.remove("username")
        storage.remove("password")
        screens.value = listOf(Screen.Login)
    }
}