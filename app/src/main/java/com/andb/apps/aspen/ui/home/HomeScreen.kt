package com.andb.apps.aspen.ui.home

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.transition
import androidx.compose.foundation.layout.offset
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.*
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.common.FabState
import com.andb.apps.aspen.ui.common.TermSwitcherFAB
import com.andb.apps.aspen.ui.home.recent.RecentsScreen
import com.andb.apps.aspen.ui.home.subjectlist.SubjectsScreen
import com.andb.apps.aspen.ui.settings.SettingsScreen
import com.andb.apps.aspen.util.ActionHandler

private val appBarOffsetKey = DpPropKey()
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
    recentState: RecentState,
    terms: List<TermState>,
    selectedTerm: Int,
    tab: HomeTab,
    expanded: Boolean,
    onFabClick: () -> Unit,
    actionHandler: ActionHandler
) {

    val fab: @Composable() () -> Unit = {
        TermSwitcherFAB(
            fabState = fabStateFromTab(tab, expanded),
            currentTerm = selectedTerm,
            onFabExpandedChanged = { onFabClick.invoke() },
            onTermChanged = { actionHandler.handle(UserAction.SwitchTerm(it)) }
        )
    }

    Scaffold(
        floatingActionButton = fab,
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            val state = transition(appBarOffsetDefinition, fabStateFromTab(tab, expanded))
            HomeAppBar(
                selectedTab = tab,
                fab = fab,
                modifier = Modifier.offset(y = state[appBarOffsetKey]),
                onItemSelected = { actionHandler.handle(UserAction.SwitchTab(it)) }
            )
        },
        bodyContent = {
            when (tab) {
                HomeTab.SUBJECTS -> {
                    when (val termState = terms.first { it.term == selectedTerm }) {
                        is TermState.Error -> SubjectsErrorScreen(termState.error, actionHandler = actionHandler)
                        is TermState.Loading -> SubjectsLoadingScreen()
                        is TermState.Data -> SubjectsScreen(subjects = subjects.filter { it.hasTerm(selectedTerm) }, term = selectedTerm, actionHandler = actionHandler)
                    }
                }
                HomeTab.RECENTS -> {
                    when (recentState) {
                        is RecentState.Error -> RecentsErrorScreen(recentState.error, actionHandler = actionHandler)
                        RecentState.Loading -> RecentsLoadingScreen()
                        is RecentState.Data -> RecentsScreen(recents = recentState.recents.toAssignments(subjects.allAssignments()), actionHandler = actionHandler)
                    }
                }
                HomeTab.SETTINGS -> SettingsScreen(actionHandler)
            }
        },
        snackbarHost = {

        }
    )
}

private fun fabStateFromTab(tab: HomeTab, expanded: Boolean) = when (tab) {
    HomeTab.SUBJECTS -> if (expanded) FabState.EXPANDED else FabState.COLLAPSED
    else -> FabState.HIDDEN
}