package com.andb.apps.aspen.state

import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel {
    val subjects: StateFlow<List<Subject>> = MutableStateFlow(listOf())
    val recents: StateFlow<List<Assignment>> = MutableStateFlow(listOf())
    val currentTab: StateFlow<HomeTab> = MutableStateFlow(HomeTab.SUBJECTS)
    val currentTerm: StateFlow<Int> = MutableStateFlow(4)
}