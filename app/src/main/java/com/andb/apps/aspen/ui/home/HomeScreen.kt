package com.andb.apps.aspen.ui.home

import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.ui.animation.DpPropKey
import androidx.ui.animation.Transition
import androidx.ui.core.Modifier
import androidx.ui.layout.offset
import androidx.ui.material.Scaffold
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.home.recent.RecentsScreen
import com.andb.apps.aspen.ui.home.subjectlist.SubjectsScreen
import com.andb.apps.aspen.ui.settings.SettingsScreen
import com.andb.apps.aspen.util.ActionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map


@Composable
fun HomeScreen(
    subjects: List<com.andb.apps.aspen.models.Subject>,
    recents: List<Assignment>,
    tab: HomeTab,
    term: Int,
    actionHandler: ActionHandler
) {
    val fabExpanded = MutableStateFlow(false)
    val fabState = fabExpanded.map { expanded ->
        fabStateFromTab(tab, expanded)
    }.collectAsState(initial = FabState.COLLAPSED)

    val appBarOffsetKey = DpPropKey()
    val definition = transitionDefinition<FabState> {
        state(FabState.COLLAPSED) {
            this[appBarOffsetKey] = 0.dp
        }
        state(FabState.EXPANDED) {
            this[appBarOffsetKey] = 64.dp
        }
        state(FabState.HIDDEN) {
            this[appBarOffsetKey] = 0.dp
        }
    }

    val fab: @Composable() ()->Unit = { HomeFab(
        fabState = fabState.value,
        currentTerm = term,
        onFabExpandedChanged = { fabExpanded.value = it },
        onTermChanged = { actionHandler.handle(UserAction.SwitchTerm(it)) }
    ) }

    Scaffold(
        floatingActionButton = fab,
        floatingActionButtonPosition = if (fabExpanded.value) Scaffold.FabPosition.End else Scaffold.FabPosition.EndDocked,
        bottomAppBar = { fabConfig ->
            Transition(definition = definition, toState = fabState.value) { state ->
                HomeAppBar(
                    selectedTab = tab,
                    fabConfig = if (fabState.value==FabState.HIDDEN) null else fabConfig,
                    fab = fab,
                    modifier = Modifier.offset(y = state[appBarOffsetKey]),
                    onItemSelected = { actionHandler.handle(UserAction.SwitchTab(it)) }
                )
            }
        },
        bodyContent = {
            when (tab) {
                HomeTab.SUBJECTS -> {
                    if (subjects.any { subject -> subject.terms.any { it.term == term } }){
                        SubjectsScreen(subjects = subjects.filter { it.hasTerm(term) }, term = term, actionHandler = actionHandler)
                    } else {
                        SubjectsLoadingScreen()
                    }
                }
                HomeTab.RECENTS -> {
                    if (recents.isNotEmpty()){
                        RecentsScreen(recents = recents, actionHandler = actionHandler)
                    } else {
                        RecentsLoadingScreen()
                    }
                }
                HomeTab.SETTINGS -> SettingsScreen(actionHandler)
            }
        }
    )
}

private fun fabStateFromTab(tab: HomeTab, expanded: Boolean) = when (tab) {
    HomeTab.SUBJECTS -> if (expanded) FabState.EXPANDED else FabState.COLLAPSED
    else -> FabState.HIDDEN
}