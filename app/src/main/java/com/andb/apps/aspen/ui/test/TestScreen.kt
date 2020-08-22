package com.andb.apps.aspen.ui.test

import androidx.animation.FastOutSlowInEasing
import androidx.animation.FloatPropKey
import androidx.animation.LinearEasing
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.remember
import androidx.compose.state
import androidx.ui.animation.DpPropKey
import androidx.ui.animation.Transition
import androidx.ui.core.Alignment
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.drawLayer
import androidx.ui.foundation.*
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.toArgb
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Settings
import androidx.ui.material.icons.filled.UnfoldLess
import androidx.ui.material.icons.filled.UnfoldMore
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.IntSize
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.ui.common.*
import com.andb.apps.aspen.ui.common.color.ExpandedColorPicker
import com.andb.apps.aspen.ui.settings.SettingsItem
import com.andb.apps.aspen.util.toHSB
import com.andb.apps.aspen.util.toggle
import kotlin.random.Random

@Composable
fun TestScreen() {
    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Test", style = MaterialTheme.typography.h6) },
            backgroundColor = MaterialTheme.colors.primary
        )
        SettingsItem(
            title = "Title",
            icon = Icons.Default.Settings,
            modifier = Modifier
                .swipeable(
                    DragDirection.Horizontal,
                    DensityAmbient.current,
                    SwipeStep(SwipeStep.Width.Size(48.dp), Color.Blue, Icons.Default.Settings)
                )
        )
        val moved = state { false }
        TestAnimation(moved.value) { moved.value = !moved.value }
        TestColorPicker()
        TestInboxItem()
        //Icon(asset = Icons.Default.MoreVert, modifier = Modifier.size(0.dp))
    }
}


private val offset = DpPropKey()
private val transition = transitionDefinition<Boolean> {
    state(true) {
        this[offset] = 100.dp
    }
    state(false) {
        this[offset] = 0.dp
    }
    transition {
        offset using tween {
            easing = LinearEasing
            duration = 1000
        }
    }
}


@Composable
private fun TestAnimation(moved: Boolean, onClick: () -> Unit) {
    Transition(definition = transition, toState = moved) { transitionState ->
        println("transitionState[offset] = ${transitionState[offset]}")
        Box(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(start = transitionState[offset])
                .size(56.dp),
            shape = CircleShape,
            backgroundColor = Color.Blue
        )
    }
}

@Preview
@Composable
private fun TestColorPicker() {
    val currentColor = state { Color(0xFF6202EE.toInt()) }
    ExpandedColorPicker(
        _selected = currentColor.value,
        modifier = Modifier.padding(horizontal = 24.dp),
        onSelect = {
            println("color updated: hsb = ${it.toHSB()}")
            currentColor.value = it
        }
    )
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .size(64.dp)
            .clickable(onClick = {
                currentColor.value = Random(21387).run {
                    Color(nextInt(255), nextInt(255), nextInt(255))
                }
            }),
        backgroundColor = currentColor.value)
}

@Preview
@Composable
private fun TestIconPicker() {
    val currentIcon = state { Subject.Icon.SCHOOL }
    IconPicker(
        selected = currentIcon.value,
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp).fillMaxWidth(),
        onSelect = { currentIcon.value = it })
}

@Preview
@Composable
private fun TestInboxItem() {

    val expanded = state { false }

    val percentExpanded = remember { FloatPropKey() }
    val oldAlpha = remember { FloatPropKey() }
    val newAlpha = remember { FloatPropKey() }
    val transitionDefinition = remember {
        transitionDefinition {
            state(false) { this[percentExpanded] = 0f; this[oldAlpha] = 1f; this[newAlpha] = 0f }
            state(true) { this[percentExpanded] = 1f; this[oldAlpha] = 0f; this[newAlpha] = 1f }
            transition {
                percentExpanded using tween<Float> { duration = 1000 }
                oldAlpha using keyframes<Float> {
                    duration = 1000
                    0f at 500 with FastOutSlowInEasing
                }
                newAlpha using keyframes<Float> {
                    duration = 1000
                    0f at 500 with FastOutSlowInEasing
                }
            }
        }
    }

    Stack(Modifier.fillMaxWidth().clickable(onClick = { expanded.toggle() })) {
        Transition(definition = transitionDefinition, toState = expanded.value) { transition ->
            Row(
                modifier = Modifier.drawLayer(alpha = transition[oldAlpha]),
                verticalGravity = Alignment.CenterVertically
            ) {
                Icon(asset = Icons.Default.UnfoldMore, modifier = Modifier.padding(24.dp))
                Text(text = "Expand", style = MaterialTheme.typography.subtitle1)
            }

            if (transition[percentExpanded] != 0f) {
                Row(
                    modifier = Modifier
                        .scale(
                            y = transition[percentExpanded],
                            //y = 0f,
                            minSize = IntSize(48, with(DensityAmbient.current) { 48.dp.toIntPx() })
                        )
                        .drawLayer(alpha = transition[newAlpha])
                        .fillMaxHeight(),
                    verticalGravity = Alignment.CenterVertically
                ) {
                    Icon(asset = Icons.Default.UnfoldLess, modifier = Modifier.padding(24.dp))
                    Text(text = "Expanded", style = MaterialTheme.typography.subtitle1)
                }
            }
        }
    }
}