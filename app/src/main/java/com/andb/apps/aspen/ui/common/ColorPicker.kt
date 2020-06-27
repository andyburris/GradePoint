package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.stateFor
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.toColorInt
import androidx.ui.animation.animate
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.draggable
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.geometry.RRect
import androidx.ui.geometry.Radius
import androidx.ui.geometry.toRect
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
                ColorGradientPicker(selected = selected, onSelect = onSelect)
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

@Composable
fun ColorPickerTextField(selected: Int, onValid: (color: Int) -> Unit) {

    val currentText =
        stateFor(v1 = selected, init = { selected.toHexString().replace("0xff", "#") })

    FilledTextField(
        value = currentText.value,
        onValueChange = { text ->
            currentText.value = text
            println("currentText.value = ${currentText.value}")
            text.toColorIntOrNull()?.let { onValid.invoke(it) }
        },
        label = { Text(text = "Hex Color") },
        leadingIcon = { Icon(asset = Icons.Filled.Colorize) },
        modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
        activeColor = Color(selected),
        isErrorValue = currentText.value.toColorIntOrNull() == null
    )
}

private fun String.toColorIntOrNull() = try {
    this.toColorInt()
} catch (e: IllegalArgumentException) {
    null
}

@Composable
fun ColorGradientPicker(selected: Int, onSelect: (color: Int) -> Unit) {
    val draggedPx = state { 0f }
    val gradientWidth = state { 0 }

    val colors = Subject.COLOR_PRESETS
        .map { Color(it) }
        .mapIndexed { index, color -> ColorStop(1f / Subject.COLOR_PRESETS.size * index, color) }

    Stack(
        //Drag stack to allow user to click anywhere on gradient and go there
        Modifier.draggable(DragDirection.Horizontal) { delta ->
            draggedPx.value = (draggedPx.value + delta).coerceIn(0f..gradientWidth.value.toFloat())
            delta
        }.padding(end = 32.dp)
    ) {
        Box(modifier = Modifier
            .height(16.dp)
            .fillMaxWidth()
            .offset(x = 16.dp)
            .gravity(Alignment.Center)
            .drawBehind {
                val gradient = HorizontalGradient(
                    colorStops = *colors.toTypedArray(),
                    startX = 0f,
                    endX = size.width
                )

                drawOutline(
                    Outline.Rounded(RRect(size.toRect(), Radius(16.dp.toPx()))),
                    gradient
                )
            }
            .onPositioned {
                gradientWidth.value = it.size.width
            }
        )
        Box(
            modifier = Modifier
                .size(32.dp)
                .offset(x = with(DensityAmbient.current) {
                    (draggedPx.value).toDp()
                }),
            shape = CircleShape,
            border = Border(2.dp, MaterialTheme.colors.onBackground),
            backgroundColor = colors.getColorAtPercent(draggedPx.value / gradientWidth.value)
        )
    }
}

private fun List<ColorStop>.getColorAtPercent(percent: Float): Color {
    if (percent.isNaN()) return first().second
    println("getting color at percent $percent")
    val segmentStart = this.filter { it.first < percent }.maxBy { it.first }
    val segmentEnd = this.filter { it.first > percent }.minBy { it.first }
    if (segmentStart == null) return segmentEnd!!.second
    if (segmentEnd == null) return segmentStart.second

    val segmentPercent = (percent - segmentStart.first) / (segmentEnd.first - segmentStart.first)

    val color = ColorUtils.blendARGB(
        segmentStart.second.toArgb(),
        segmentEnd.second.toArgb(),
        segmentPercent
    )
    println("percent $percent blends ${segmentStart.second.toArgb().toHexString()} and ${segmentEnd.second.toArgb().toHexString()} to ${color.toHexString()}")
    return Color(color).copy(alpha = 1f)
}
