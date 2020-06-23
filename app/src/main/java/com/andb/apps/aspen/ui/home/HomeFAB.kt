package com.andb.apps.aspen.ui.home

import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.ui.animation.DpPropKey
import androidx.ui.animation.Transition
import androidx.ui.core.Modifier
import androidx.ui.core.drawLayer
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.padding
import androidx.ui.material.ExtendedFloatingActionButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.FilterList
import androidx.ui.unit.dp
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
        fabSize using tween { duration = 199 }
        termExpansion using tween { duration = 199 }
    }
}


@Composable
fun HomeFab(fabState: FabState, currentTerm: Int, onFabExpandedChanged: (Boolean) -> Unit, onTermChanged: (Int) -> Unit) {
    Transition(definition = definition, toState = fabState) { transitionState ->
        ExtendedFloatingActionButton(
            icon = {
                Icon(asset = Icons.Default.FilterList)
            },
            text = {
                Text(
                    text = "Term ${if (transitionState[termExpansion] < .5f) currentTerm else " "}".toUpperCase(),
                    maxLines = 1,
                    color = MaterialTheme.colors.onPrimary
                )
                HomeTermSwitcher(
                    currentTerm = currentTerm,
                    modifier = Modifier
                        .scale(x = transitionState[termExpansion])
                        .drawLayer(alpha = transitionState[termExpansion])
                        .padding(start = 16.dp),
                    onTermSwitch = onTermChanged
                )
            },
            backgroundColor = MaterialTheme.colors.primary,
            onClick = {
                onFabExpandedChanged.invoke(fabState != FabState.EXPANDED)
            },
            modifier = Modifier.padding(top = transitionState[fabOffsetKey]).scaleConstraints(transitionState[fabSize], transitionState[fabSize])
        )

    }
}