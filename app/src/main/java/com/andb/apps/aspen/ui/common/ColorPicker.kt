package com.andb.apps.aspen.ui.common

import androidx.compose.animation.animate
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.RRect
import androidx.compose.ui.geometry.Radius
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.toHexString
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.toColorInt
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.ui.common.color.HuePicker
import com.andb.apps.aspen.util.HSB
import com.andb.apps.aspen.util.toColor
import com.andb.apps.aspen.util.toHSL


@Composable
fun ColorPicker(selected: Int, modifier: Modifier = Modifier, onSelect: (color: Int) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
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





