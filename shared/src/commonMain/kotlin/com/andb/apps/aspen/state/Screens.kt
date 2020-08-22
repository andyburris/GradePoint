package com.andb.apps.aspen.state

import com.andb.apps.aspen.data.repository.AspenRepository
import com.andb.apps.aspen.models.*
import com.andb.apps.aspen.newIOThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject

class Screens : KoinComponent{
    private val aspenRepository: AspenRepository by inject()

    val stack: MutableStateFlow<List<Screen>> = MutableStateFlow(listOf())
    val currentScreen: Flow<Screen> get() = stack.map { it.last() }

    init {
        when (aspenRepository.loggedIn) {
            true -> loadData()
            false -> stack.value = listOf(Screen.Login)
        }
    }

    operator fun plusAssign(action: Action){
        handleAction(action)
    }

    fun handleAction(action: Action): Boolean{
        when(action){
            is UserAction.Back -> {
                if (this.stack.value.size <= 1) return false
                stack.value = stack.value.dropLast(1)
                return true
            }
            is UserAction.SwitchTab -> {
                stack.value += stack.value.map {
                    when(it){
                        is Screen.Home -> it.copy(tab = action.tab)
                        else -> it
                    }
                }
            }
            is UserAction.OpenScreen -> stack.value += action.screen
            is UserAction.SwitchTerm -> {
                stack.updateEach {
                    when(it){
                        is Screen.Home -> it.copy(term = action.term)
                        is Screen.Subject -> it.copy(term = action.term)
                        else -> it
                    }
                }
            }
            is UserAction.Login -> login(action.username, action.password)
            is UserAction.EditConfig -> editConfig(action.config)
            is UserAction.Logout -> logout()
            is DataAction.TermLoaded -> {
                stack.updateEach { screen ->
                    when(screen){
                        is Screen.Home -> screen.copy(subjects = screen.subjects.combineWith(action.subjects))
                        is Screen.Subject -> {
                            val newTerm = action.subjects.find { it.id == screen.subject.id } ?: return@updateEach screen
                            return@updateEach screen.copy(subject = screen.subject + newTerm)
                        }
                        else -> screen
                    }
                }
            }
            is DataAction.RecentsLoaded -> {
                stack.updateEach {
                    when(it){
                        is Screen.Home -> it.copy(recentItems = it.recentItems + action.recents)
                        else -> it
                    }
                }
            }
        }
        return true
    }

    private fun login(username: String, password: String){
        stack.value = listOf(Screen.Home(listOf(), listOf(), HomeTab.SUBJECTS, term = 1))
        newIOThread {
            val loggedIn = aspenRepository.attemptLogin(username, password)
            if (loggedIn){
                loadData()
            } else {
                stack.value = listOf(Screen.Login)
            }
        }
    }

    private fun logout(){
        aspenRepository.logout()
        stack.value = listOf(Screen.Login)
    }

    //TODO: move this into separate class (side effect?), login() should only set to loading screen, other actions should be external
    private fun loadData(){
        stack.value = listOf(Screen.Home(listOf(), listOf(), HomeTab.SUBJECTS, term = 1))
        newIOThread {
            val currentTermSubjects = aspenRepository.getTerm(null)
            this@Screens += DataAction.TermLoaded(currentTermSubjects)
            val currentTerm = currentTermSubjects.first().terms.indexOfFirst { it is Term.WithGrades } + 1
            this@Screens.handleAction(UserAction.SwitchTerm(currentTerm))
            newIOThread {
                val recentAssignments = aspenRepository.getRecentAssignments()
                this@Screens += DataAction.RecentsLoaded(recentAssignments)
            }
            for (term in listOf(1, 2, 3, 4) - currentTerm){
                newIOThread {
                    val termSubjects = aspenRepository.getTerm(term)
                    this@Screens += DataAction.TermLoaded(termSubjects)
                }
            }
        }
    }

    private fun editConfig(config: Subject.Config){
        stack.updateEach { screen ->
            when (screen) {
                is Screen.Home -> {
                    val updatedSubjects = screen.subjects.map { subject ->
                        if (subject.id == config.id) subject.copy(config = config) else subject
                    }
                    screen.copy(subjects = updatedSubjects)
                }
                else -> screen
            }
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