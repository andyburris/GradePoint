package com.andb.apps.aspen.ui.common

import androidx.ui.animation.animatedFloat
import androidx.ui.core.Modifier
import androidx.ui.core.composed
import androidx.ui.core.drawBehind
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.draggable
import androidx.ui.geometry.Size
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.offset
import androidx.ui.unit.*

fun Modifier.swipeable(
    direction: DragDirection,
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
            dragDirection = direction,
            onDragStarted = { println("drag started") },
            onDragStopped = {
                if (with(density) { dragPx.value.toDp() } > threshold) {
                    currentStep().action.invoke()
                }
                dragPx.animateTo(0f)
            },
            startDragImmediately = dragPx.isRunning
        ) { delta ->
            val maxDrag = steps.map { it.width.toPx(density) }.maxBy { it }?.value ?: 0f
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

        fun toPx(density: Density): Px {
            return when (this) {
                is Size -> with(density) { this@Width.width.toPx() }
                else -> Int.MAX_VALUE.px
            }
        }
    }

    enum class Edge {
        SCREEN, VIEW
    }
}