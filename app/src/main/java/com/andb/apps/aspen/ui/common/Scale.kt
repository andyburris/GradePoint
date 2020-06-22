package com.andb.apps.aspen.ui.common

import androidx.compose.state
import androidx.ui.core.*
import androidx.ui.unit.*

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

fun Modifier.scale(
    x: Float = 1f,
    y: Float = 1f,
    minSize: IntPxSize = IntPxSize(0.ipx, 0.ipx),
    transformOrigin: TransformOrigin = TransformOrigin.Center
) = composed {
    val lastMeasure = state<IntPxSize?> { null }
    this + Scale(x, y, transformOrigin, minSize, lastMeasure.value) { if (lastMeasure.value == null) lastMeasure.value = it }
}

fun Modifier.scaleConstraints(x: Float = 1f, y: Float = 1f) = this + ScaleConstraints(x, y)

private class MeasureModifier(val onMeasure: (IntPxSize) -> Unit) : LayoutModifier{
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
        layoutDirection: LayoutDirection
    ): MeasureScope.MeasureResult {
        val placeable = measurable.measure(constraints, layoutDirection)
        onMeasure.invoke(IntPxSize(placeable.width, placeable.height))
        return layout(placeable.width, placeable.height){
            placeable.place(IntPxPosition.Origin)
        }
    }
}


private data class Scale(
    private val x: Float,
    private val y: Float,
    private val transformOrigin: TransformOrigin,
    private val minSize: IntPxSize,
    val lastMeasure: IntPxSize?,
    val onMeasure: (IntPxSize) -> Unit
) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
        layoutDirection: LayoutDirection
    ): MeasureScope.MeasureResult {
        //println("scale - minSize = $minSize")
        val scaledConstraints = lastMeasure?.let {
            val width = (it.width - minSize.width) * x + minSize.width
            val height = (it.height - minSize.height) * y + minSize.height
            Constraints.fixed(width, height)
        } ?: constraints
        val placeable: Placeable = measurable.measure(scaledConstraints, layoutDirection)
        onMeasure.invoke(IntPxSize(placeable.width, placeable.height))
        //lastMeasure.value = IntPxSize(placeable.width, placeable.height)
        //Log.d("scaleModifier", "constraints = $constraints, scaledConstraints = $scaledConstraints; placeable - width = ${placeable.width}, height = ${placeable.height}")
        return layout(width = placeable.width * x, height = placeable.height * y) {
            //placeable.place(-placeable.width / 2 * (1 - x), -placeable.height / 2 * (1 - y))
            placeable.place(0.ipx, 0.ipx)
        }
    }
}

private data class ScaleConstraints(private val x: Float, private val y: Float) : LayoutModifier{
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
        layoutDirection: LayoutDirection
    ): MeasureScope.MeasureResult {
        val scaledConstraints = Constraints(
            minWidth = constraints.minWidth * x,
            maxWidth = constraints.maxWidth * x,
            minHeight = constraints.minHeight * y,
            maxHeight = constraints.maxHeight * y
        )
        val placeable: Placeable = measurable.measure(scaledConstraints, layoutDirection)
        return layout(width = placeable.width * x, height = placeable.height * y){
            placeable.place(-placeable.width / 2 * (1 - x), -placeable.height / 2 * (1 - y))
        }
    }

}

private fun IntPx.toDp(density: Density) = with(density) { this@toDp.toDp() }