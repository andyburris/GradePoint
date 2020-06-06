package com.andb.apps.aspen.ui.common

import android.util.Log
import androidx.ui.core.*
import androidx.ui.unit.Density
import androidx.ui.unit.IntPx

/*
fun Modifier.scale(x: Float, y: Float, density: Density): Modifier = composed {

    var defaultSize: IntPxSize? = null
    this.fillMaxSize()
    this.onPositioned { defaultSize = it.size; println("size changed to $defaultSize") } + when(defaultSize){
        null -> {
            println("defaultSize = $defaultSize (null?)")
            Modifier
        }
        else -> {
            val scaledX = (defaultSize!!.width * x).toDp(density)
            val scaledY = (defaultSize!!.height * y).toDp(density)
            println("scaledX = $scaledX, scaledY = $scaledY")
            preferredSize(scaledX, scaledY)
        }
    }

}
*/

fun Modifier.scale(x: Float = 1f, y: Float = 1f) = this + Scale(x, y)

private data class Scale(private val x: Float, private val y: Float) : LayoutModifier{
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
        layoutDirection: LayoutDirection
    ): MeasureScope.MeasureResult {
        val scaledConstraints =  Constraints(
            minWidth = constraints.minWidth * x,
            maxWidth = constraints.maxWidth * x,
            minHeight = constraints.minHeight * y,
            maxHeight = constraints.maxHeight * y
        )
        val placeable: Placeable = measurable.measure(scaledConstraints, layoutDirection)
        //Log.d("scaleModifier", "constraints = $constraints, scaledConstraints = $scaledConstraints; placeable - width = ${placeable.width}, height = ${placeable.height}")
        return layout(width = placeable.width * x, height = placeable.height * y){
            placeable.place(-placeable.width / 2 * (1-x), -placeable.height / 2 * (1-y))
        }
    }

}

private fun IntPx.toDp(density: Density) = with(density){ this@toDp.toDp() }