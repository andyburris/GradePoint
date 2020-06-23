package com.andb.apps.aspen.ui.home

import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.ui.animation.Transition
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import androidx.ui.unit.sp

private val termBackgroundKey = FloatPropKey()
private val termTransition = transitionDefinition<Int> {
    state(1){ this[termBackgroundKey] = 1f }
    state(2){ this[termBackgroundKey] = 2f }
    state(3){ this[termBackgroundKey] = 3f }
    state(4){ this[termBackgroundKey] = 4f }
}

@Composable
fun HomeTermSwitcher(currentTerm: Int, modifier: Modifier = Modifier, onTermSwitch: (Int)->Unit) {
    Transition(definition = termTransition, toState = currentTerm) { transitionState ->
        Stack(modifier = modifier) {
            Box(
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .padding(start = 48.dp * (transitionState[termBackgroundKey] - 1) + 6.dp)
                    .size(36.dp)
                    .gravity(Alignment.CenterStart)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.gravity(Alignment.Center)
            ) {
                TermItem(1, Modifier.clickable(onClick = { onTermSwitch.invoke(1) }))
                TermItem(2, Modifier.clickable(onClick = { onTermSwitch.invoke(2) }))
                TermItem(3, Modifier.clickable(onClick = { onTermSwitch.invoke(3) }))
                TermItem(4, Modifier.clickable(onClick = { onTermSwitch.invoke(4) }))
            }
        }
    }
}

@Composable
private fun TermItem(term: Int, modifier: Modifier = Modifier){
    val numberStyle = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center, fontSize = 16.sp)
    Stack(modifier = modifier.size(48.dp)) {
        Text(
            text = term.toString(),
            style = numberStyle,
            modifier = Modifier.gravity(Alignment.Center)
        )
    }
}