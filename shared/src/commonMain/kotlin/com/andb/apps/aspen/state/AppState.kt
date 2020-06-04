package com.andb.apps.aspen.state

import com.andb.apps.aspen.data.repository.AspenRepository
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.newIOThread
import com.andb.apps.aspen.printThrowable
import io.ktor.client.features.ClientRequestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject

object AppState : KoinComponent {

    private val aspenRepository: AspenRepository by inject()

    private var screens: MutableStateFlow<List<Screen>> = MutableStateFlow(listOf())
    val currentScreen get() = screens.map { it.last() }


    init {
        when (aspenRepository.loggedIn) {
            true -> navigateHome()
            false -> navigateLogin()
        }
    }

    fun openSubject(subject: Subject) {
        screens.value += Screen.Subject(subject)
    }

    fun openAssignment(assignment: Assignment) {
        screens.value += Screen.Assignment(assignment)
    }

    fun openTest() {
        screens.value += Screen.Test
    }

    fun goBack(): Boolean = when {
        screens.value.size > 1 -> {
            screens.value = screens.value.dropLast(1); true
        }
        else -> false
    }

    private fun navigateHome() {
        if (screens.value.lastOrNull() !is Screen.Loading) screens.value += Screen.Loading
        newIOThread {
            val (subjects, recents) = aspenRepository.getResponse()
            screens.value =
                listOf(Screen.Home(subjects, recents, MutableStateFlow(HomeTab.SUBJECTS)))
        }
    }

    private fun navigateLogin() {
        screens.value = listOf(Screen.Login)
    }

    fun updateSubjectConfig(newConfig: Subject.Config) {
        screens.value = screens.value.map { screen ->
            when (screen) {
                is Screen.Home -> {
                    val updatedSubjects = screen.subjects.map { subject ->
                        if (subject.id == newConfig.id) {
                            subject.copy(config = newConfig)
                        } else {
                            subject
                        }
                    }
                    screen.copy(subjects = updatedSubjects)
                }
                else -> screen
            }
        }
        aspenRepository.updateSubjectConfig(newConfig)
    }

    fun login(username: String, password: String) {
        screens.value += Screen.Loading
        newIOThread {
            try {
                when (aspenRepository.attemptLogin(username, password)) {
                    true -> navigateHome()
                    false -> navigateLogin()
                }
            } catch (e: ClientRequestException) {
                printThrowable(e)
                navigateLogin()
            }
        }
    }

    fun logout() {
        aspenRepository.logout()
        navigateLogin()
    }
}