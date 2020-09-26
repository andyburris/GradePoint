package com.andb.apps.aspen.ui.common.color

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Box
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Radius
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.dragGestureFilter
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

@Composable
fun SaturationBrightnessPicker(hue: Float, saturation: Float, brightness: Float, modifier: Modifier = Modifier, onChange: (saturation: Float, lightness: Float) -> Unit) {
    val (boxSize, setBoxSize) = remember { mutableStateOf(IntSize(0, 0)) }
//    val dragPosition = stateFor(saturation, brightness, boxSize) { Pair(boxSize.width * saturation, boxSize.height * (1f - brightness))
    val thumbSize = with(DensityAmbient.current) { 24.dp.toIntPx() }
    val dragPosition = Offset(
        x = (boxSize.width - thumbSize) * saturation,
        y = (boxSize.height - thumbSize) * (1f - brightness)
    )

    val update : (newX: Float, newY: Float) -> Unit = { newX, newY ->
        val slideableWidth = boxSize.width - thumbSize
        val slideableHeight = boxSize.height - thumbSize
        val boundedX = newX.coerceIn(0f..slideableWidth.toFloat())
        val boundedY = newY.coerceIn(0f..slideableHeight.toFloat())
        //percent dragged from bottom left
        val xPct = boundedX / slideableWidth
        val yPct = 1f - boundedY / slideableHeight
        onChange.invoke(xPct, yPct)
    }

    Box(
        modifier = modifier
            .dragGestureFilter(object : DragObserver {
                override fun onDrag(dragDistance: Offset): Offset {
                    val newX = (dragPosition.x + dragDistance.x).coerceIn(0f..boxSize.width.toFloat())
                    val newY = (dragPosition.y + dragDistance.y).coerceIn(0f..boxSize.height.toFloat())
                    //dragPosition = Pair(newX, newY)
                    update(newX, newY)
                    return dragDistance
                }
            })
            .pressIndicatorGestureFilter(onStart = { pointer ->
                val onThumb = pointer.x in dragPosition.x..(dragPosition.x + thumbSize) && pointer.y in dragPosition.y..(dragPosition.y + thumbSize)
                if (!onThumb) {
                    println()
                    update(pointer.x, pointer.y)
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
                setBoxSize(it.size.run { IntSize(width, height) })
            }
    ) {
        val offsetDp = with(DensityAmbient.current) {
            val positionPx = dragPosition
            Pair(positionPx.x.toDp(), positionPx.y.toDp())
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
