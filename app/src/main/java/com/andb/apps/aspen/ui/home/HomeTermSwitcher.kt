package com.andb.apps.aspen.ui.home

import androidx.compose.animation.Transition
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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