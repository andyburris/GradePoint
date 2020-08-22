package com.andb.apps.aspen.ui.test

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.Transition
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.UnfoldLess
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.ui.common.*
import com.andb.apps.aspen.ui.common.color.ExpandedColorPicker
import com.andb.apps.aspen.ui.settings.SettingsItem
import com.andb.apps.aspen.util.toHSB
import com.andb.apps.aspen.util.toggle
import kotlin.random.Random


@Composable
fun TestScreen() {
    ScrollableColumn(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Test", style = MaterialTheme.typography.h6) },
            backgroundColor = MaterialTheme.colors.primary
        )
        SettingsItem(
            title = "Title",
            icon = Icons.Default.Settings,
            modifier = Modifier
                .swipeable(
                    Orientation.Horizontal,
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
        offset using tween(
            easing = LinearEasing,
            durationMillis = 1000
        )
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
        transitionDefinition<Boolean> {
            state(false) { this[percentExpanded] = 0f; this[oldAlpha] = 1f; this[newAlpha] = 0f }
            state(true) { this[percentExpanded] = 1f; this[oldAlpha] = 0f; this[newAlpha] = 1f }
            transition {
                percentExpanded using tween(durationMillis = 1000)
                oldAlpha using keyframes {
                    durationMillis = 1000
                    0f at 500 with FastOutSlowInEasing
                }
                newAlpha using keyframes<Float> {
                    durationMillis = 1000
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