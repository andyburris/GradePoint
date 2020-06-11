package com.andb.apps.aspen.ui.home

import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.*
import androidx.ui.animation.DpPropKey
import androidx.ui.animation.Transition
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.offset
import androidx.ui.layout.padding
import androidx.ui.material.ExtendedFloatingActionButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.FilterList
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.ui.common.scale
import com.andb.apps.aspen.ui.common.scaleConstraints
import com.andb.apps.aspen.ui.home.recent.RecentsScreen
import com.andb.apps.aspen.ui.home.subjectlist.SubjectsScreen
import com.andb.apps.aspen.ui.settings.SettingsScreen
import com.andb.apps.aspen.util.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

private enum class FabState {
    HIDDEN, COLLAPSED, EXPANDED
}

@Composable
fun HomeScreen(homeScreen: Screen.Home) {
    val currentTab: State<HomeTab> = homeScreen.currentTab.collectAsState()
    val fabExpanded = MutableStateFlow(false)
    val fabState = homeScreen.currentTab
        .map { fabStateFromTab(it) }
        .combine(fabExpanded) { fabState, expanded ->
            if (fabState == FabState.COLLAPSED && expanded) FabState.EXPANDED else fabState
        }
        .collectAsState(initial = FabState.COLLAPSED)
    val appBarOffsetKey = DpPropKey()
    val fabOffsetKey = DpPropKey()
    val fabSize = FloatPropKey()
    val termExpansion = FloatPropKey()
    val definition = transitionDefinition<FabState> {
        state(FabState.COLLAPSED) {
            this[appBarOffsetKey] = 0.dp
            this[fabOffsetKey] = 0.dp
            this[fabSize] = 1f
            this[termExpansion] = 0f
        }
        state(FabState.EXPANDED) {
            this[appBarOffsetKey] = 64.dp
            this[fabOffsetKey] = 32.dp
            this[fabSize] = 1f
            this[termExpansion] = 1f
        }
        state(FabState.HIDDEN) {
            this[appBarOffsetKey] = 0.dp
            this[fabOffsetKey] = 0.dp
            this[fabSize] = 0f
            this[termExpansion] = 0f
        }

        transition {
            fabSize using tween { duration = 199 }
            termExpansion using tween { duration = 199 }
        }
    }

    val fab: @Composable() () -> Unit = {
        Transition(definition = definition, toState = fabState.value) { transitionState ->
            ExtendedFloatingActionButton(
                icon = {
                    Icon(asset = Icons.Default.FilterList)
                },
                text = {
                    Text(text = "Term".toUpperCase(), maxLines = 1, color = MaterialTheme.colors.onPrimary)
                    HomeTermSwitcher(
                        Modifier
                        .scale(x = transitionState[termExpansion])
                        .padding(start = 24.dp)
                        //.clipToBounds()
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                onClick = {
                    fabExpanded.value = !fabExpanded.value
                },
                modifier = Modifier.padding(top = transitionState[fabOffsetKey]).scaleConstraints(transitionState[fabSize], transitionState[fabSize])
            )

        }
    }
    Scaffold(
        floatingActionButton = fab,
        floatingActionButtonPosition = if (fabExpanded.value) Scaffold.FabPosition.End else Scaffold.FabPosition.EndDocked,
        bottomAppBar = { fabConfig ->
            Transition(definition = definition, toState = fabState.value) { state ->
                HomeAppBar(
                    selectedTab = currentTab.value,
                    fabConfig = if (fabState.value == FabState.HIDDEN) null else fabConfig,
                    fab = fab,
                    modifier = Modifier.offset(y = state[appBarOffsetKey])
                ) {
                    homeScreen.switchTab(it)
                }
            }
        },
        bodyContent = {
            when (currentTab.value) {
                HomeTab.SUBJECTS -> SubjectsScreen(subjects = homeScreen.subjects)
                HomeTab.RECENTS -> RecentsScreen(recents = homeScreen.recents)
                HomeTab.SETTINGS -> SettingsScreen()
            }
        }
    )
}

private fun fabStateFromTab(tab: HomeTab) = when (tab) {
    HomeTab.SUBJECTS -> FabState.COLLAPSED
    else -> FabState.HIDDEN
}