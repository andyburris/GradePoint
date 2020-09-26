package com.andb.apps.aspen.ui.common

import androidx.compose.animation.*
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
fun TermSwitcherFAB(fabState: FabState, currentTerm: Int, possibleTerms: List<Int> = (1..4).toList(), onFabExpandedChanged: (Boolean) -> Unit, onTermChanged: (Int) -> Unit) {
    val transitionState = transition(definition, fabState)
    ExtendedFloatingActionButton(
        icon = {
            Icon(asset = Icons.Default.FilterList)
        },
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Term ${if (transitionState[termExpansion] < .5f) currentTerm else " "}".toUpperCase(),
                    maxLines = 1,
                    color = MaterialTheme.colors.onPrimary
                )
                AnimatedVisibility(visible = fabState == FabState.EXPANDED, enter = expandHorizontally()) {
                    HomeTermSwitcher(
                        currentTerm = currentTerm,
                        possibleTerms = possibleTerms,
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
    )
}

private val termBackgroundKey = FloatPropKey()
private val termTransition = transitionDefinition<Int> {
    state(1) { this[termBackgroundKey] = 1f }
    state(2) { this[termBackgroundKey] = 2f }
    state(3) { this[termBackgroundKey] = 3f }
    state(4) { this[termBackgroundKey] = 4f }
}

@Composable
fun HomeTermSwitcher(currentTerm: Int, possibleTerms: List<Int>, modifier: Modifier = Modifier, onTermSwitch: (Int) -> Unit) {
    val transitionState = transition(termTransition, possibleTerms.indexOf(currentTerm) + 1)
    Stack(modifier = modifier) {
        Box(
            shape = CircleShape,
            backgroundColor = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .padding(start = 48.dp * (transitionState[termBackgroundKey] - 1) + 6.dp)
                .size(36.dp)
                .align(Alignment.CenterStart)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.align(Alignment.Center)
        ) {
            possibleTerms.forEach {
                TermItem(it, Modifier.clickable(onClick = { onTermSwitch.invoke(it) }))
            }
        }
    }
}

@Composable
private fun TermItem(term: Int, modifier: Modifier = Modifier) {
    val numberStyle = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center, fontSize = 16.sp)
    Stack(modifier = modifier.size(48.dp)) {
        Text(
            text = term.toString(),
            style = numberStyle,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}