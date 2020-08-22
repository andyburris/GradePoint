package com.andb.apps.aspen.ui.common.color

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.drawBehind
import androidx.ui.core.drawShadow
import androidx.ui.foundation.Border
import androidx.ui.foundation.Box
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.geometry.Radius
import androidx.ui.graphics.Color
import androidx.ui.graphics.ColorStop
import androidx.ui.graphics.VerticalGradient
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.padding
import androidx.ui.layout.size
import androidx.ui.layout.width
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.andb.apps.aspen.ui.common.AlternativeSlider
import com.andb.apps.aspen.util.HSB
import com.andb.apps.aspen.util.toColor

@Composable
fun HuePicker(colors: List<Color>, hue: Float, modifier: Modifier = Modifier, onSelect: (hue: Float) -> Unit) {
    val colorStops = colors.mapIndexed { index, color -> ColorStop(1f / colors.size * index, color) }

    AlternativeSlider(
        position = hue,
        orientation = DragDirection.Vertical,
        track = {
            Box(modifier = Modifier
                .width(32.dp)
                .fillMaxHeight()
                .gravity(Alignment.Center)
                .drawBehind {
                    val gradient = VerticalGradient(
                        colorStops = *colorStops.toTypedArray(),
                        startY = 0f,
                        endY = size.height
                    )

                    drawRoundRect(gradient, radius = Radius(16.dp.toPx()))
                }
            )
        },
        thumb = {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(28.dp)
                    .drawShadow(2.dp, shape = CircleShape),
                shape = CircleShape,
                border = Border(3.dp, MaterialTheme.colors.background),
                backgroundColor = HSB(hue, 1f, 1f).toColor()
            )
        },
        onChange = onSelect,
        modifier = modifier.fillMaxHeight()
    )
}