package com.andb.apps.aspen.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.platform.AnimationClockAmbient
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.ui.common.scale
import com.andb.apps.aspen.ui.common.scaleConstraints


enum class FabState {
    HIDDEN, COLLAPSED, EXPANDED
}

private val fabOffsetKey = DpPropKey()
private val fabSize = FloatPropKey()
private val termExpansion = FloatPropKey()
private val definition = transitionDefinition<FabState> {
    state(FabState.COLLAPSED) {
        this[fabOffsetKey] = 0.dp
        this[fabSize] = 1f
        this[termExpansion] = 0f
    }
    state(FabState.EXPANDED) {
        this[fabOffsetKey] = 32.dp
        this[fabSize] = 1f
        this[termExpansion] = 1f
    }
    state(FabState.HIDDEN) {
        this[fabOffsetKey] = 0.dp
        this[fabSize] = 0f
        this[termExpansion] = 0f
    }

    transition {
        fabSize using tween(durationMillis = 199)
        termExpansion using tween(durationMillis = 199)
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeFab(fabState: FabState, currentTerm: Int, onFabExpandedChanged: (Boolean) -> Unit, onTermChanged: (Int) -> Unit) {
    val transitionState = transition(definition, fabState)
    ExtendedFloatingActionButton(
        /*icon = {
            Icon(asset = Icons.Default.FilterList, modifier = Modifier.size(0.dp))
        },*/
        text = {
            Row(verticalGravity = Alignment.CenterVertically) {
                Text(
                    text = "Term ${if (transitionState[termExpansion] < .5f) currentTerm else " "}".toUpperCase(),
                    maxLines = 1,
                    color = MaterialTheme.colors.onPrimary
                )
                AnimatedVisibility(visible = fabState == FabState.EXPANDED, enter = expandHorizontally()) {
                    HomeTermSwitcher(
                        currentTerm = currentTerm,
                        modifier = Modifier.padding(start = 16.dp),
                        onTermSwitch = onTermChanged
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        onClick = {
            onFabExpandedChanged.invoke(fabState != FabState.EXPANDED)
        },
        modifier = Modifier
            .padding(top = transitionState[fabOffsetKey])
            .scaleConstraints(transitionState[fabSize], transitionState[fabSize])
        //.scaleConstraints(0.001f, 0.001f)
    )
}