package com.andb.apps.aspen.ui.test

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Settings
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.ui.assignment.TopAppBarWithStatusBar
import com.andb.apps.aspen.ui.common.ColorPicker
import com.andb.apps.aspen.ui.common.IconPicker
import com.andb.apps.aspen.ui.common.SwipeStep
import com.andb.apps.aspen.ui.common.swipeable
import com.andb.apps.aspen.ui.settings.SettingsItem

@Composable
fun TestScreen() {
    Column(Modifier.fillMaxSize()) {
        TopAppBarWithStatusBar(title = { Text(text = "Test") })
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
private fun TestIconPicker(){
    val currentIcon = state { Subject.Icon.SCHOOL }
    IconPicker(selected = currentIcon.value, modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp).fillMaxWidth(), onSelect = { currentIcon.value = it })
}