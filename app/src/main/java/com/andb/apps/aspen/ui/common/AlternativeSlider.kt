package com.andb.apps.aspen.ui.common

import androidx.compose.foundation.Box
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.StackScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.pressIndicatorGestureFilter
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.onPositioned
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.dp


@Composable
fun AlternativeSlider(position: Float, orientation: Orientation = Orientation.Horizontal, modifier: Modifier = Modifier, track: @Composable() StackScope.() -> Unit, thumb: @Composable() StackScope.() -> Unit, onChange: (Float) -> Unit){
    val (trackSize, setTrackSize) = remember { mutableStateOf(0) }
    val (thumbSize, setThumbSize) = remember { mutableStateOf(0) }
    val draggedPx = (trackSize - thumbSize) * position

    val update: (position: Float) -> Unit = { position ->
        val slideableWidth = (trackSize - thumbSize).toFloat()
        val bounded = position.coerceIn(0f..slideableWidth)
        val percent = bounded / slideableWidth
        println("updating slider - slideable width = $slideableWidth, bounded = $bounded, percent = $percent")
        onChange.invoke(if (!percent.isNaN()) percent else 0f)
    }

    Stack(
        modifier = modifier.draggable(orientation) { delta ->
            val newPx = (draggedPx + delta)
            update(newPx)
        }.pressIndicatorGestureFilter(onStart = {
            val position = if (orientation == Orientation.Horizontal) it.x else it.y
            val onThumb = position in draggedPx..(draggedPx + thumbSize)
            if (!onThumb) {
                update(position)
            }
        })
    ) {
        Box(Modifier.onPositioned { setTrackSize(it.size.run { if(orientation == Orientation.Horizontal) width else height }) }) {
            track()
        }
        val offset = with(DensityAmbient.current) { draggedPx.toDp() }
        Box(
            modifier = Modifier
                .onPositioned { setThumbSize(it.size.run { if(orientation == Orientation.Horizontal) width else height }) }
                .offset(
                    x = if (orientation == Orientation.Horizontal) offset else 0.dp,
                    y = if (orientation == Orientation.Vertical) offset else 0.dp
                )
        ) {
            thumb()
        }
    }
}