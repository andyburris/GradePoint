package com.andb.apps.aspen.ui.common

import androidx.compose.animation.animatedFloat
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.drawBehind
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.swipeable(
    direction: Orientation,
    density: Density,
    vararg steps: SwipeStep,
    threshold: Dp = 24.dp
): Modifier = composed {
    val dragPx = animatedFloat(initVal = 0f)
    fun currentStep() = steps.first()
    this
        .drawBehind {
            val step = currentStep()
            drawRect(step.color, size = Size(width = dragPx.value, height = this.size.height))
            //val currentDragDp = with(DensityAmbient.current) { dragPx.value.toDp() }
            /*if (step.icon == null) return@drawBehind
            VectorPainter(asset = step.icon)
            this.drawCanvas { canvas, size ->
                canvas.drawImage(ImageAsset(step.icon.defaultWidth, step.icon.defaultHeight))
                step.icon.root.
            }*/
        }
        .offset(x = with(density) { dragPx.value.toDp() })
        .draggable(
            orientation = direction,
            onDragStarted = { println("drag started") },
            onDragStopped = {
                if (with(density) { dragPx.value.toDp() } > threshold) {
                    currentStep().action.invoke()
                }
                dragPx.animateTo(0f)
            },
            startDragImmediately = dragPx.isRunning
        ) { delta ->
            val maxDrag = steps.map { it.width.toPx(density) }.maxBy { it } ?: 0f
            dragPx.snapTo((dragPx.value + delta).coerceIn(0f..maxDrag))
            delta
        }

}

class SwipeStep(
    val width: Width,
    val color: Color = Color.Black,
    val icon: VectorAsset? = null,
    val iconColor: Color = Color.White,
    val edge: Edge = Edge.VIEW,
    val action: () -> Unit = {}
) {
    sealed class Width {
        class Size(val width: Dp) : Width()
        object Fill : Width()

        fun toPx(density: Density): Float {
            return when (this) {
                is Size -> with(density) { this@Width.width.toPx() }
                else -> Float.MAX_VALUE
            }
        }
    }

    enum class Edge {
        SCREEN, VIEW
    }
}