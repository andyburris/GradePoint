package com.andb.apps.aspen.state

import com.andb.apps.aspen.data.repository.AspenRepository
import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.Term
import com.andb.apps.aspen.newIOThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject

data class Screens(private val stack: MutableStateFlow<List<Screen>>) : KoinComponent{
    private val aspenRepository: AspenRepository by inject()

    init {
        when (aspenRepository.loggedIn) {
            true -> loadData()
            false -> stack.value = listOf(Screen.Login)
        }
    }

    val currentScreen: Flow<Screen> get() = stack.map { it.last() }

    operator fun plusAssign(partialState: PartialState){
        when(partialState){
            is HomeState -> reduceHomeState(partialState)
        }
    }

    private fun reduceHomeState(homeState: HomeState){
        val currentHomeScreen = stack.value.filterIsInstance<Screen.Home>().first()
        val reducedState = when(homeState){
            is SubjectListState -> currentHomeScreen.let { it.copy(subjects = it.subjects.combineWith(homeState.subjects)) }
            is RecentAssignmentsState -> currentHomeScreen.let { it.copy(recentItems = it.recentItems + homeState.recents) }
            is TabState -> currentHomeScreen.copy(tab = homeState.tab)
        }
        println("reducing home state: \ncurrent = $currentHomeScreen, \npartial = $homeState, \nreduced = $reducedState")
        stack.value = stack.value.map { screen ->
            when(screen){
                is Screen.Home -> reducedState
                is Screen.Subject ->{
                    if (homeState !is SubjectListState) return@map screen
                    val newSubject = homeState.subjects.find { it.id == screen.subject.id }
                    return@map if (newSubject != null) screen.copy(subject = screen.subject + newSubject) else screen
                }
                else -> screen
            }
        }
    }

    fun handleAction(action: UserAction): Boolean{
        when(action){
            is UserAction.Back -> {
                if (this.stack.value.size <= 1) return false
                stack.value = stack.value.dropLast(1)
                return true
            }
            is UserAction.SwitchTab -> this += TabState(action.tab)
            is UserAction.OpenScreen -> stack.value += action.screen
            is UserAction.SwitchTerm -> {
                stack.value = stack.value.map {
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

    private fun loadData(){
        stack.value = listOf(Screen.Home(listOf(), listOf(), HomeTab.SUBJECTS, term = 1))
        newIOThread {
            val currentTermSubjects = aspenRepository.getTerm(null)
            this@Screens += SubjectListState(currentTermSubjects)
            val currentTerm = currentTermSubjects.first().terms.indexOfFirst { it is Term.WithGrades } + 1
            this@Screens.handleAction(UserAction.SwitchTerm(currentTerm))
            newIOThread {
                val recentAssignments = aspenRepository.getRecentAssignments()
                this@Screens += RecentAssignmentsState(recentAssignments)
            }
            for (term in listOf(1, 2, 3, 4) - currentTerm){
                newIOThread {
                    val termSubjects = aspenRepository.getTerm(term)
                    this@Screens += SubjectListState(termSubjects)
                }
            }
        }
    }

    private fun editConfig(config: Subject.Config){
        stack.value = stack.value.map { screen ->
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
}