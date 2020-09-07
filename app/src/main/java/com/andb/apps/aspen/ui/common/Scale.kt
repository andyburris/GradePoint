package com.andb.apps.aspen.ui.common

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.state
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize

/*
fun Modifier.scale(x: Float, y: Float, density: Density): Modifier = composed {

    var defaultSize: IntSize? = null
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
    minSize: IntSize = IntSize(0, 0),
    transformOrigin: TransformOrigin = TransformOrigin.Center
) = composed {
    val lastMeasure = remember { mutableStateOf<IntSize?>(null) }
    this + Scale(x, y, transformOrigin, minSize, lastMeasure.value) { if (lastMeasure.value == null) lastMeasure.value = it }
}

fun Modifier.scaleConstraints(x: Float = 1f, y: Float = 1f) = this + ScaleConstraints(x, y)

private class MeasureModifier(val onMeasure: (IntSize) -> Unit) : LayoutModifier{
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureScope.MeasureResult {
        val placeable = measurable.measure(constraints)
        onMeasure.invoke(IntSize(placeable.width, placeable.height))
        return layout(placeable.width, placeable.height){
            placeable.place(Offset.Zero)
        }
    }
}


private data class Scale(
    private val x: Float,
    private val y: Float,
    private val transformOrigin: TransformOrigin,
    private val minSize: IntSize,
    val lastMeasure: IntSize?,
    val onMeasure: (IntSize) -> Unit
) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureScope.MeasureResult {
        //println("scale - minSize = $minSize")
        val scaledConstraints = lastMeasure?.let {
            val width = (it.width - minSize.width) * x + minSize.width
            val height = (it.height - minSize.height) * y + minSize.height
            Constraints.fixed(width.toInt(), height.toInt())
        } ?: constraints
        val placeable: Placeable = measurable.measure(scaledConstraints)
        onMeasure.invoke(IntSize(placeable.width, placeable.height))
        //lastMeasure.value = IntSize(placeable.width, placeable.height)
        //Log.d("scaleModifier", "constraints = $constraints, scaledConstraints = $scaledConstraints; placeable - width = ${placeable.width}, height = ${placeable.height}")
        return layout(width = (placeable.width * x).toInt(), height = (placeable.height * y).toInt()) {
            //placeable.place(-placeable.width / 2 * (1 - x), -placeable.height / 2 * (1 - y))
            placeable.place(0, 0)
        }
    }
}

private data class ScaleConstraints(private val x: Float, private val y: Float) : LayoutModifier{
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureScope.MeasureResult {
        val scaledConstraints = Constraints(
            minWidth = (constraints.minWidth * x).toInt(),
            maxWidth = (constraints.maxWidth * x).toInt(),
            minHeight = (constraints.minHeight * y).toInt(),
            maxHeight = (constraints.maxHeight * y).toInt()
        )
        val scaledConstraints2 = Constraints(
            minWidth = 0,
            maxWidth = 106,
            minHeight = 0,
            maxHeight = 500
        )

        val placeable: Placeable = measurable.measure(scaledConstraints)
        return layout(width = (placeable.width * x).toInt(), height = (placeable.height * y).toInt()){
            placeable.place(
                x = (-placeable.width / 2 * (1 - x)).toInt(),
                y = (-placeable.height / 2 * (1 - y)).toInt()
            )
        }
    }

}

private fun Int.toDp(density: Density) = with(density) { this@toDp.toDp() }