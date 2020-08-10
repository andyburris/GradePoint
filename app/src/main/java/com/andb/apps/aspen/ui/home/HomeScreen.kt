package com.andb.apps.aspen.ui.home

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.Transition
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.home.recent.RecentsScreen
import com.andb.apps.aspen.ui.home.subjectlist.SubjectsScreen
import com.andb.apps.aspen.ui.settings.SettingsScreen
import com.andb.apps.aspen.util.ActionHandler

private val appBarOffsetKey =  DpPropKey()
private val appBarOffsetDefinition = transitionDefinition<FabState> {
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

@Composable
fun HomeScreen(
    subjects: List<Subject>,
    recents: List<Assignment>,
    term: Int,
    tab: HomeTab,
    expanded: Boolean,
    onFabClick: () -> Unit,
    actionHandler: ActionHandler
) {

    val fab: @Composable() () -> Unit = {
        HomeFab(
            fabState = fabStateFromTab(tab, expanded),
            currentTerm = term,
            onFabExpandedChanged = { onFabClick.invoke() },
            onTermChanged = { actionHandler.handle(UserAction.SwitchTerm(it)) }
        )
    }

    Scaffold(
        floatingActionButton = fab,
        floatingActionButtonPosition = Scaffold.FabPosition.End,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            Transition(definition = appBarOffsetDefinition, toState = fabStateFromTab(tab, expanded)) { state ->
                HomeAppBar(
                    selectedTab = tab,
                    fab = fab,
                    modifier = Modifier.offset(y = state[appBarOffsetKey]),
                    onItemSelected = { actionHandler.handle(UserAction.SwitchTab(it)) }
                )
            }
        },
        bodyContent = {
            when (tab) {
                HomeTab.SUBJECTS -> {
                    if (subjects.any { it.hasTerm(term) }) {
                        SubjectsScreen(subjects = subjects.filter { it.hasTerm(term) }, term = term, actionHandler = actionHandler)
                    } else {
                        SubjectsLoadingScreen()
                    }
                }
                HomeTab.RECENTS -> {
                    if (recents.isNotEmpty()) {
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