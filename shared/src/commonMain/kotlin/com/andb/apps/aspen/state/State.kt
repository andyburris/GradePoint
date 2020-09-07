package com.andb.apps.aspen.state

import com.andb.apps.aspen.data.repository.AspenRepository
import com.andb.apps.aspen.models.*
import com.andb.apps.aspen.newIOThread
import com.andb.apps.aspen.util.result.onFailure
import com.andb.apps.aspen.util.result.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject

class State : KoinComponent {
    private val aspenRepository: AspenRepository by inject()

    val stack: MutableStateFlow<List<Screen>> = MutableStateFlow(listOf())
    val currentScreen: Flow<Screen> get() = stack.map { it.last() }

    init {
        when (aspenRepository.loggedIn) {
            true -> loadData()
            false -> stack.value = listOf(Screen.Login())
        }
    }

    operator fun plusAssign(action: Action) {
        handleAction(action)
    }

    fun handleAction(action: Action): Boolean {
        when (action) {
            is UserAction.Back -> {
                if (this.stack.value.size <= 1) return false
                stack.value = stack.value.dropLast(1)
                return true
            }
            is UserAction.SwitchTab -> {
                stack.value += stack.value.map {
                    when (it) {
                        is Screen.Home -> it.copy(tab = action.tab)
                        else -> it
                    }
                }
            }
            is UserAction.OpenScreen -> stack.value += action.screen
            is UserAction.SwitchTerm -> {
                stack.updateEach {
                    when (it) {
                        is Screen.Home -> it.copy(selectedTerm = action.term)
                        is Screen.Subject -> it.copy(term = action.term)
                        else -> it
                    }
                }
            }
            is UserAction.Login -> login(action.username, action.password)
            is UserAction.EditConfig -> editConfig(action.config)
            UserAction.Reload -> loadData()
            is UserAction.HideSubject -> aspenRepository.updateSubjectConfig(action.subject.config.copy(isHidden = true))
            is UserAction.UnhideSubject -> aspenRepository.updateSubjectConfig(action.subject.config.copy(isHidden = false))
            is UserAction.Logout -> logout()
            is DataAction.TermLoaded -> {
                stack.updateEach { screen ->
                    when (screen) {
                        is Screen.Home -> {
                            val subjects = screen.subjects.combineWith(action.subjects)
                            val terms = screen.updatedTerm(action.term, TermState.Data(action.term))
                            screen.copy(subjects = subjects, terms = terms)
                        }
                        is Screen.Subject -> {
                            val newTerm = action.subjects.find { it.id == screen.subject.id }
                                ?: return@updateEach screen
                            return@updateEach screen.copy(subject = screen.subject + newTerm)
                        }
                        else -> screen
                    }
                }
            }
            is DataAction.RecentsLoaded -> stack.updateScreen<Screen.Home> {
                val oldRecents = (it.recentState as? RecentState.Data)?.recents ?: emptyList()
                it.copy(recentState = RecentState.Data(oldRecents + action.recents))
            }
            DataAction.LoginFailed -> stack.updateScreen<Screen.Login> { it.copy(error = "Incorrect Username or Password") }
            is DataAction.Offline -> {
                when(action.at) {
                    OfflineAt.Login -> stack.updateScreen<Screen.Login> { it.copy(error = "Offline") }
                    OfflineAt.Current -> stack.updateScreen<Screen.Home> { screen -> screen.copy(terms = (1..4).map { TermState.Error(it, AspenError.OFFLINE) }) }
                    is OfflineAt.Term -> stack.updateScreen<Screen.Home> { it.copy(terms = it.updatedTerm(action.at.term, TermState.Error(action.at.term, AspenError.OFFLINE))) }
                    OfflineAt.Recents -> stack.updateScreen<Screen.Home> { it.copy(recentState = RecentState.Error(AspenError.OFFLINE)) }
                }
            }
            is DataAction.AspenError -> {
                stack.updateEach {
                    it
                }
            }
        }
        return true
    }

    private fun login(username: String, password: String) {
        stack.value = listOf(Screen.Home.Loading)
        newIOThread {
            aspenRepository.attemptLogin(username, password)
                .onSuccess { loggedIn ->
                    if (loggedIn) {
                        loadData()
                    } else {
                        this@State += DataAction.LoginFailed
                    }
                }.onFailure { exception ->
                    this@State += DataAction.Offline(OfflineAt.Login)
                }
        }
    }

    private fun logout() {
        aspenRepository.logout()
        stack.value = listOf(Screen.Login())
    }

    //TODO: move this into separate class (side effect?), login() should only set to loading screen, other actions should be external
    private fun loadData() {
        stack.value = listOf(Screen.Home.Loading)
        newIOThread {
            aspenRepository.getTerm(null).onSuccess { currentTermSubjects ->
                val currentTerm = currentTermSubjects.first().terms.indexOfFirst { it is Term.WithGrades } + 1
                this@State += DataAction.TermLoaded(currentTerm, currentTermSubjects)
                this@State.handleAction(UserAction.SwitchTerm(currentTerm))
                newIOThread {
                    aspenRepository.getRecentAssignments().onSuccess { recentAssignments ->
                        this@State += DataAction.RecentsLoaded(recentAssignments)
                    }.onFailure {
                        this@State += DataAction.Offline(OfflineAt.Recents)
                    }
                }
                for (term in listOf(1, 2, 3, 4) - currentTerm) {
                    newIOThread {
                        aspenRepository.getTerm(term).onSuccess { termSubjects ->
                            this@State += DataAction.TermLoaded(term, termSubjects)
                        }.onFailure {
                            this@State += DataAction.Offline(OfflineAt.Term(term))
                        }
                    }
                }
            }.onFailure {
                this@State += DataAction.Offline(OfflineAt.Current)
            }
        }
    }

    private fun editConfig(config: Subject.Config) {
        stack.updateScreen<Screen.Home> {
            val updatedSubjects = it.subjects.map { subject ->
                if (subject.id == config.id) subject.copy(config = config) else subject
            }
            it.copy(subjects = updatedSubjects)
        }
        aspenRepository.updateSubjectConfig(config)
    }

    override fun toString(): String {
        return stack.value.toString()
    }
}

private fun MutableStateFlow<List<Screen>>.updateEach(transform: (Screen) -> Screen) {
    value = value.map(transform)
}

private inline fun <reified T> MutableStateFlow<List<Screen>>.updateScreen(transform: (T) -> Screen) {
    value = value.map {
        when (it) {
            is T -> transform(it)
            else -> it
        }
    }
}