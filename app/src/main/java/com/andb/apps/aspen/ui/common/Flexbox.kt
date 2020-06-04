package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.ui.core.Layout
import androidx.ui.core.Modifier
import androidx.ui.unit.PxPosition
import androidx.ui.unit.ipx

@Composable
fun Flexbox(modifier: Modifier = Modifier, children: @Composable() ()->Unit){
    Layout(children = children, modifier = modifier) { measurables, constraints, _ ->
        val placeables = measurables.map { it.measure(constraints) }

        var placedWidth = 0.ipx
        val rowHeights = mutableListOf(0.ipx)
        val positions = placeables.map { placeable ->
            if (placedWidth + placeable.width > constraints.maxWidth) {
                rowHeights.add(0.ipx)
                placedWidth = 0.ipx
            }

            val position = PxPosition(placedWidth, rowHeights.dropLast(1).sumBy { it.value }.ipx)
            placedWidth += placeable.width
            if (placeable.height > rowHeights.last()) rowHeights[rowHeights.size - 1] =
                placeable.height
            return@map position
        }

        layout(constraints.maxWidth, rowHeights.sumBy { it.value }.ipx) {
            placeables.forEachIndexed { i, placeable -> placeable.place(positions[i]) }
        }
    }
}