package com.andb.apps.aspen.ui.test

import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.animation.Transition
import androidx.ui.core.Alignment
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.DropdownMenu
import androidx.ui.material.DropdownMenuItem
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.MoreVert
import androidx.ui.material.icons.filled.Settings
import androidx.ui.material.icons.filled.UnfoldLess
import androidx.ui.material.icons.filled.UnfoldMore
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.ui.common.*
import com.andb.apps.aspen.ui.settings.SettingsItem
import com.andb.apps.aspen.util.toggle

@Composable
fun TestScreen() {
    Column(Modifier.fillMaxSize()) {
        TopAppBarWithStatusBar(title = { Text(text = "Test", style = MaterialTheme.typography.h6) })
        SettingsItem(
            title = "Title",
            icon = Icons.Default.Settings,
            modifier = Modifier
                .swipeable(
                    DragDirection.Horizontal,
                    DensityAmbient.current,
                    SwipeStep(SwipeStep.Width.Size(48.dp), Color.Blue, Icons.Default.Settings)
                )
                .padding(horizontal = 24.dp, vertical = 16.dp)
        )
        TestColorPicker()
        TestIconPicker()
        TestInboxItem()
        TestDropdown()
    }
}

@Preview
@Composable
private fun TestColorPicker() {
    val currentColor = state { 0xFFEF5350.toInt() }
    ColorPicker(
        selected = currentColor.value,
        modifier = Modifier.padding(horizontal = 24.dp),
        onSelect = { currentColor.value = it })
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

@Composable
private fun TestDropdown() {
    val expanded = state { false }
    val onClick = { println("Test 1 clicked"); expanded.value = false }
    DropdownMenu(
        toggle = {
            Icon(
                asset = Icons.Default.MoreVert,
                modifier = Modifier.clickable(onClick = { expanded.value = true }).padding(24.dp)
            )
        },
        expanded = expanded.value, onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(onClick = onClick) {
            Text(text = "Test 1")
        }
        DropdownMenuItem(onClick = onClick) {
            Text(text = "Test 2")
        }
    }
}

@Preview
@Composable
private fun TestInboxItem() {

    val expanded = state { false }

    val percentExpanded = FloatPropKey()
    val transitionDefinition = transitionDefinition {
        state(false) { this[percentExpanded] = 0f }
        state(true) { this[percentExpanded] = 1f }
        transition {
            percentExpanded using tween<Float> { duration = 200 }
        }
    }

    Stack(Modifier.fillMaxWidth().clickable(onClick = { expanded.toggle() })) {
        Transition(definition = transitionDefinition, toState = expanded.value) { transition ->
            Row(
                modifier = Modifier.drawBackground(Color.Blue),
                verticalGravity = Alignment.CenterVertically
            ) {
                Icon(asset = Icons.Default.UnfoldMore, modifier = Modifier.padding(24.dp))
                Text(text = "Expand", style = MaterialTheme.typography.subtitle1)
            }

            if (transition[percentExpanded] != 0f) {
                Row(
                    modifier = Modifier
                        .drawBackground(Color.Red)
                        .scale(y = transition[percentExpanded]).fillMaxHeight(),
                    verticalGravity = Alignment.CenterVertically
                ) {
                    Icon(asset = Icons.Default.UnfoldLess, modifier = Modifier.padding(24.dp))
                    Text(text = "Expanded", style = MaterialTheme.typography.subtitle1)
                }
            }
        }
    }
}