package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.stateFor
import androidx.core.graphics.toColorInt
import androidx.ui.animation.animate
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.*
import androidx.ui.layout.*
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Colorize
import androidx.ui.material.icons.filled.Done
import androidx.ui.material.icons.filled.KeyboardArrowDown
import androidx.ui.unit.dp
import androidx.ui.util.toHexString
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.ui.common.color.HuePicker
import com.andb.apps.aspen.util.HSB
import com.andb.apps.aspen.util.toColor
import com.andb.apps.aspen.util.toHSL


@Composable
fun ColorPicker(selected: Int, modifier: Modifier = Modifier, onSelect: (color: Int) -> Unit) {
    val expanded = state { false }
    Column(modifier) {
        Text(
            text = "Color",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Flexbox(verticalGravity = Alignment.CenterVertically) {
            if (!expanded.value) {
                for (color in Subject.COLOR_PRESETS) {
                    Box(shape = CircleShape,
                        backgroundColor = Color(color),
                        border = Border(1.dp, MaterialTheme.colors.onBackground),
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
                    hue = selected.toHSL().first,
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





