package com.andb.apps.aspen.ui.subject

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.Transition
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

@Preview
@Composable
fun SubjectTermSelector(modifier: Modifier = Modifier, onSelect: (term: Int) -> Unit) {

    val currentTerm = mutableStateOf(4)

    Row(verticalGravity = Alignment.CenterVertically) {
            Text(
                text = "TERM",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(end = 8.dp)
            )

        Stack(
            modifier = Modifier.height(24.dp)
                .background(MaterialTheme.colors.primary, RoundedCornerShape(12.dp))
        ) {
            val termKey = DpPropKey()
            val definition = transitionDefinition<Int> {
                state(1) { this[termKey] = 8.dp }
                state(2) { this[termKey] = 32.dp }
                state(3) { this[termKey] = 56.dp }
                state(4) { this[termKey] = 80.dp }

                transition {
                    termKey using tween(durationMillis = 200)
                }
            }

            Transition(definition = definition, toState = currentTerm.value) {
                Box(
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.fillMaxHeight().padding(start = it[termKey]).width(24.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalGravity = Alignment.CenterVertically,
                modifier = Modifier
                    .gravity(Alignment.Center)
                    .height(24.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Box(Modifier.clickable(onClick = { currentTerm.value = 1; onSelect.invoke(1) })){
                        Text(
                            text = "1",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp).width(8.dp)
                        )
                    }
                Box(Modifier.clickable(onClick = { currentTerm.value = 2; onSelect.invoke(2) })) {
                    Text(
                        text = "2",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp).width(8.dp)
                    )
                }
                Box(Modifier.clickable(onClick = { currentTerm.value = 3; onSelect.invoke(3) })) {
                    Text(
                        text = "3",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp).width(8.dp)
                    )
                }
                Box(Modifier.clickable(onClick = { currentTerm.value = 4; onSelect.invoke(4) })) {
                    Text(
                        text = "4",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp).width(8.dp)
                    )
                }
            }
        }
    }
}