package com.andb.apps.aspen.ui.common

import androidx.compose.animation.animate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.ui.common.color.*


@Composable
fun ColorPicker(selected: Int, modifier: Modifier = Modifier, onSelect: (color: Int) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    Column(modifier) {
        Text(
            text = "Color",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Flexbox(verticalAlignment = Alignment.CenterVertically) {
            if (!expanded.value) {
                for (color in Subject.COLOR_PRESETS) {
                    Box(shape = CircleShape,
                        backgroundColor = Color(color),
                        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                        gravity = ContentGravity.Center,
                        modifier = Modifier.padding(end = 4.dp, bottom = 4.dp).size(32.dp)
                            .clickable(
                                onClick = {
                                    onSelect.invoke(color)
                                }
                            )) {
                        if (selected == color) {
                            Icon(asset = Icons.Default.Done)
                        }
                    }
                }
            } else {
                HuePicker(
                    colors = Subject.COLOR_PRESETS.map { Color(it) },
                    hue = Color(selected).toHSB().hue,
                    onSelect = { onSelect.invoke(HSB(it, 1f, 1f).toColor().toArgb()) }
                )
            }

            Icon(
                asset = Icons.Default.KeyboardArrowDown,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable(onClick = { expanded.value = !expanded.value })
                    .drawLayer(rotationZ = animate(target = if (expanded.value) 180f else 0f))
            )
        }
    }
}





