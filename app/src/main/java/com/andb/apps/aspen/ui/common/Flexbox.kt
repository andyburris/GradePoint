package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Layout
import androidx.ui.core.Modifier
import androidx.ui.core.Placeable
import androidx.ui.unit.IntPx
import androidx.ui.unit.PxPosition
import androidx.ui.unit.ipx

@Composable
fun Flexbox(
    modifier: Modifier = Modifier,
    verticalGravity: Alignment.Vertical = Alignment.Top,
    children: @Composable() () -> Unit
) {
    Layout(children = children, modifier = modifier) { measurables, constraints, _ ->
        val placeables = measurables.map { it.measure(constraints) }

        var placedWidth = 0.ipx
        val rowHeights = mutableListOf(0.ipx)
        val positions = placeables.map { placeable ->
            if (placedWidth + placeable.width > constraints.maxWidth) {
                rowHeights.add(0.ipx)
                placedWidth = 0.ipx
            }

            val position = FlexboxPosition(placeable, placedWidth, rowHeights.size - 1)
            placedWidth += placeable.width
            if (placeable.height > rowHeights.last()) rowHeights[rowHeights.size - 1] = placeable.height
            return@map position
        }

        layout(constraints.maxWidth, rowHeights.sumBy { it.value }.ipx) {
            positions.forEach { it.placeable.place(it.getPosition(verticalGravity, rowHeights)) }
        }
    }
}

private class FlexboxPosition(val placeable: Placeable, val x: IntPx, val row: Int){
    fun getPosition(verticalGravity: Alignment.Vertical, rowHeights: List<IntPx>): PxPosition{
        val rowTop = rowHeights.slice(0 until row).sumBy { it.value }.ipx
        val currentRowHeight = rowHeights[row]
        val itemOffset = when(verticalGravity){
            Alignment.Top -> 0.ipx
            Alignment.CenterVertically -> (currentRowHeight - placeable.height)/2
            Alignment.Bottom -> currentRowHeight - placeable.height
            else -> 0.ipx
        }
        return PxPosition(x, rowTop + itemOffset)
    }
}