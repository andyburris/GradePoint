package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.stateFor
import androidx.core.graphics.toColorInt
import androidx.ui.animation.animate
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.drawBehind
import androidx.ui.core.drawLayer
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.geometry.RRect
import androidx.ui.geometry.Radius
import androidx.ui.geometry.toRect
import androidx.ui.graphics.Color
import androidx.ui.graphics.HorizontalGradient
import androidx.ui.graphics.Outline
import androidx.ui.graphics.drawOutline
import androidx.ui.layout.*
import androidx.ui.material.FilledTextField
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Colorize
import androidx.ui.material.icons.filled.Done
import androidx.ui.material.icons.filled.KeyboardArrowDown
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.Px
import androidx.ui.unit.dp
import androidx.ui.util.toHexString
import com.andb.apps.aspen.models.Subject


@Composable
fun ColorPicker(selected: Int, modifier: Modifier = Modifier, onSelect: (color: Int) -> Unit) {
    val expanded = state { false }
    Column(modifier) {
        Text(
            text = "Color",
            style = TextStyle(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )

        Row(
            verticalGravity = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!expanded.value) {
                for (color in Subject.COLOR_PRESETS) {
                    Box(shape = CircleShape,
                        backgroundColor = Color(color),
                        border = Border(1.dp, Color.Black),
                        gravity = ContentGravity.Center,
                        modifier = Modifier.padding(end = 4.dp).size(32.dp).clickable(
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
                Box(modifier = Modifier
                    .height(32.dp)
                    .weight(1f)
                    .drawBehind {
                        drawOutline(
                            Outline.Rounded(RRect(size.toRect(), Radius.circular(16.dp.toPx().value))),
                            HorizontalGradient(
                                colors = Subject.COLOR_PRESETS.map { Color(it) },
                                startX = Px.Zero.value,
                                endX = size.width
                            )
                        )
                    }
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

        if (expanded.value) {
            ColorPickerTextField(selected = selected, onValid = onSelect)
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