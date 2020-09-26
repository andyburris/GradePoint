package com.andb.apps.aspen.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Layout
import androidx.compose.ui.Modifier
import androidx.compose.ui.Placeable
import androidx.compose.ui.geometry.Offset


@Composable
fun Flexbox(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    children: @Composable() () -> Unit
) {
    Layout( modifier = modifier, children = children) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }

        var placedWidth = 0
        val rowHeights = mutableListOf(0)
        val positions = placeables.map { placeable ->
            if (placedWidth + placeable.width > constraints.maxWidth) {
                rowHeights.add(0)
                placedWidth = 0
            }

            val position = FlexboxPosition(placeable, placedWidth, rowHeights.size - 1)
            placedWidth += placeable.width
            if (placeable.height > rowHeights.last()) rowHeights[rowHeights.size - 1] = placeable.height
            return@map position
        }

        layout(constraints.maxWidth, rowHeights.sumBy { it }) {
            positions.forEach { it.placeable.place(it.getPosition(verticalAlignment, rowHeights)) }
        }
    }
}

private class FlexboxPosition(val placeable: Placeable, val x: Int, val row: Int){
    fun getPosition(verticalAlignment: Alignment.Vertical, rowHeights: List<Int>): Offset{
        val rowTop = rowHeights.slice(0 until row).sumBy { it }
        val currentRowHeight = rowHeights[row]
        val itemOffset = when(verticalAlignment){
            Alignment.Top -> 0
            Alignment.CenterVertically -> (currentRowHeight - placeable.height)/2
            Alignment.Bottom -> currentRowHeight - placeable.height
            else -> 0
        }
        return Offset(x.toFloat(), (rowTop + itemOffset).toFloat())
    }
}