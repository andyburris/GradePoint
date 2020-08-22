package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.compose.remember
import androidx.compose.state
import androidx.compose.stateFor
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Layout
import androidx.ui.core.Modifier
import androidx.ui.core.gesture.pressIndicatorGestureFilter
import androidx.ui.core.onPositioned
import androidx.ui.foundation.Box
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.draggable
import androidx.ui.geometry.Offset
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Slider
import androidx.ui.unit.Dp
import androidx.ui.unit.dp

@Composable
fun AlternativeSlider(position: Float, orientation: DragDirection = DragDirection.Horizontal, modifier: Modifier = Modifier, track: @Composable() StackScope.() -> Unit, thumb: @Composable() StackScope.() -> Unit, onChange: (Float) -> Unit){
    val (trackSize, setTrackSize) = state { 0 }
    val (thumbSize, setThumbSize) = state { 0 }
    val draggedPx = stateFor(trackSize, thumbSize) { (trackSize - thumbSize) * position }

    fun update() {
        onChange.invoke(draggedPx.value / (trackSize - thumbSize).toFloat())
    }

    Stack(
        modifier = modifier.draggable(orientation) { delta ->
            val newPx = (draggedPx.value + delta)
            draggedPx.value = newPx.coerceIn(0f..(trackSize - thumbSize).toFloat())
            update()
            delta
        }.pressIndicatorGestureFilter(onStart = {
            val position = if (orientation == DragDirection.Horizontal) it.x else it.y
            val onThumb = position in draggedPx.value..(draggedPx.value + thumbSize)
            if (!onThumb) {
                draggedPx.value = position.coerceIn(0f..(trackSize - thumbSize).toFloat())
                update()
            }
        })
    ) {
        Box(Modifier.onPositioned { setTrackSize(it.size.run { if(orientation == DragDirection.Horizontal) width else height }) }) {
            track()
        }
        val offset = with(DensityAmbient.current) { draggedPx.value.toDp() }
        Box(
            modifier = Modifier
                .onPositioned { setThumbSize(it.size.run { if(orientation == DragDirection.Horizontal) width else height }) }
                .offset(
                    x = if (orientation == DragDirection.Horizontal) offset else 0.dp,
                    y = if (orientation == DragDirection.Vertical) offset else 0.dp
                )
        ) {
            thumb()
        }
    }
}