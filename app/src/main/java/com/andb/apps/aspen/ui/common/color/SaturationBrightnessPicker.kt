package com.andb.apps.aspen.ui.common.color

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Box
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.state
import androidx.compose.runtime.stateFor
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.drawBehind
import androidx.compose.ui.geometry.Radius
import androidx.compose.ui.gesture.pressIndicatorGestureFilter
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorStop
import androidx.compose.ui.graphics.HorizontalGradient
import androidx.compose.ui.graphics.VerticalGradient
import androidx.compose.ui.onPositioned
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.util.HSB
import com.andb.apps.aspen.util.toColor

@Composable
fun SaturationBrightnessPicker(hue: Float, saturation: Float, brightness: Float, modifier: Modifier = Modifier, onChange: (saturation: Float, lightness: Float) -> Unit) {
    val (boxSize, setBoxSize) = state { IntSize(0, 0) }
    val dragPosition = stateFor(boxSize) { Pair(boxSize.width * saturation, boxSize.height * (1f - brightness)) }
    val thumbSize = with(DensityAmbient.current) { 24.dp.toIntPx() }

    fun update() {
        //percent dragged from bottom left
        val xPct = dragPosition.value.first / boxSize.width
        val yPct = 1f - dragPosition.value.second / boxSize.height
        onChange.invoke(xPct, yPct)
    }

    Box(
        modifier = modifier
            .draggable(Orientation.Horizontal) { delta ->
                val newPx = (dragPosition.value.first + delta).coerceIn(0f..boxSize.width.toFloat())
                dragPosition.value = dragPosition.value.copy(first = newPx)
                update()
                delta
            }
            .draggable(Orientation.Vertical) { delta ->
                val newPx = (dragPosition.value.second + delta).coerceIn(0f..boxSize.height.toFloat())
                dragPosition.value = dragPosition.value.copy(second = newPx)
                update()
                delta
            }
            .pressIndicatorGestureFilter(onStart = { pointer ->
                val onThumb = dragPosition.value.let { pointer.x in it.first..(it.first + thumbSize) && pointer.y in it.second..(it.second + thumbSize) }
                if (!onThumb) {
                    dragPosition.value = Pair(pointer.x.coerceIn(0f..boxSize.width.toFloat()), pointer.y.coerceIn(0f..boxSize.height.toFloat()))
                    update()
                }
            })
            .drawBehind {
                val saturationGradient = HorizontalGradient(
                    colorStops = *arrayOf(ColorStop(0f, Color.Transparent), ColorStop(1f, HSB(hue, 1f, 1f).toColor())),
                    startX = 0f,
                    endX = size.width
                )
                val lightnessGradient = VerticalGradient(
                    colorStops = *arrayOf(ColorStop(0f, Color.Transparent), ColorStop(1f, Color.Black)),
                    startY = 0f,
                    endY = size.height
                )

                drawRoundRect(Color.White, radius = Radius(12.dp.toPx()))
                drawRoundRect(saturationGradient, radius = Radius(12.dp.toPx()))
                drawRoundRect(lightnessGradient, radius = Radius(12.dp.toPx()))
            }
            .onPositioned {
                setBoxSize(it.size.run { IntSize(width - thumbSize, height - thumbSize) })
            }
    ) {
        val offsetDp = with(DensityAmbient.current) {
            val positionPx = dragPosition.value
            Pair(positionPx.first.toDp(), positionPx.second.toDp())
        }
        Box(
            modifier = Modifier
                .offset(offsetDp.first, offsetDp.second)
                .size(24.dp)
                .drawShadow(2.dp, shape = CircleShape),
            shape = CircleShape,
            backgroundColor = HSB(hue, saturation, brightness).toColor(),
            border = BorderStroke(3.dp, Color.White)
        )
    }
}
