package com.andb.apps.aspen.ui.home

import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.ui.animation.DpPropKey
import androidx.ui.animation.Transition
import androidx.ui.core.Modifier
import androidx.ui.layout.offset
import androidx.ui.material.Scaffold
import androidx.ui.unit.dp
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
        floatingActionButtonPosition = Scaffold.FabPosition.EndDocked,
        bottomAppBar = { fabConfig ->
            Transition(definition = appBarOffsetDefinition, toState = fabStateFromTab(tab, expanded)) { state ->
                HomeAppBar(
                    selectedTab = tab,
                    fabConfig = if (tab == HomeTab.SUBJECTS) fabConfig else null,
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