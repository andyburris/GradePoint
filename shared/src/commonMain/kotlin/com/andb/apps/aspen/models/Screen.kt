package com.andb.apps.aspen.models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class Screen {
    object Login : Screen()
    object Loading : Screen()
    data class Home(val subjects: List<com.andb.apps.aspen.models.Subject>, val recents: List<com.andb.apps.aspen.models.Assignment>, private val tab: MutableStateFlow<HomeTab>) : Screen(){
        val currentTab: StateFlow<HomeTab> get() = tab
        fun switchTab(newTab: HomeTab){
            tab.value = newTab
        }
    }
    data class Subject(val subject: com.andb.apps.aspen.models.Subject) : Screen()
    data class Assignment(val assignment: com.andb.apps.aspen.models.Assignment) : Screen()
    object Test : Screen()
}

enum class HomeTab {
    SUBJECTS, RECENTS, SETTINGS
}
