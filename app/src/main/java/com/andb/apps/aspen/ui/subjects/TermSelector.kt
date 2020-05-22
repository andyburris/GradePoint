package com.andb.apps.aspen.ui.subjects

import androidx.animation.FastOutSlowInEasing
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.mutableStateOf
import androidx.ui.animation.DpPropKey
import androidx.ui.animation.Transition
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp

@Preview
@Composable
fun TermSelector(modifier: Modifier) {

    val currentTerm = mutableStateOf(4)

    Surface(modifier = modifier) {
        Column(horizontalGravity = Alignment.Start) {
            Text(
                text = "TERM",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Stack(modifier = Modifier.height(24.dp)) {
                Box(
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxSize()
                )

                val termKey = DpPropKey()

                val transitionDefinition = transitionDefinition<Int> {
                    state(1) { this[termKey] = 10.dp }
                    state(2) { this[termKey] = (10 + 24 + 4).dp }
                    state(3) { this[termKey] = (10 + 2 * (24 + 4)).dp }
                    state(4) { this[termKey] = (10 + 3 * (24 + 4)).dp }

                    transition {
                        termKey using tween {
                            easing = FastOutSlowInEasing
                            duration = 200
                        }
                    }
                }
                Transition(
                    definition = transitionDefinition,
                    toState = currentTerm.value
                ) { transitionState ->
                    println("transitionState changed padding to ${transitionState[termKey]}dp")
                    Box(
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.fillMaxHeight()
                            .padding(start = transitionState[termKey]).width(24.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth().gravity(Alignment.Center)
                ) {
                    Clickable(onClick = { currentTerm.value = 1 }) {
                        Text(text = "1", color = MaterialTheme.colors.onPrimary)
                    }
                    Clickable(onClick = { currentTerm.value = 2 }) {
                        Text(text = "2", color = MaterialTheme.colors.onPrimary)
                    }
                    Clickable(onClick = { currentTerm.value = 3 }) {
                        Text(text = "3", color = MaterialTheme.colors.onPrimary)
                    }
                    Clickable(onClick = { currentTerm.value = 4 }) {
                        Text(text = "4", color = MaterialTheme.colors.onPrimary)
                    }
                }
            }
        }
    }
}