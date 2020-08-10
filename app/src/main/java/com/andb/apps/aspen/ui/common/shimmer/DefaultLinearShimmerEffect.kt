package com.andb.apps.aspen.ui.common.shimmer

import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.core.graphics.transform
import kotlin.math.tan


/**
 * Used to specify this type of [ShimmerEffect] for [shimmer].
 */
object DefaultLinearShimmerEffectFactory : ShimmerEffectFactory {
    override fun create(
        baseAlpha: Float,
        highlightAlpha: Float,
        direction: Direction,
        dropOff: Float,
        intensity: Float,
        tilt: Float,
        durationMs: Int,
        delay: Int,
        repeatMode: RepeatMode,
        clock: AnimationClockObservable
    ): ShimmerEffect {
        return DefaultLinearShimmerEffect(
            baseAlpha,
            highlightAlpha,
            direction,
            dropOff,
            intensity,
            tilt,
            durationMs,
            delay,
            repeatMode,
            clock
        )
    }
}

private class DefaultLinearShimmerEffect(
    val baseAlpha: Float,
    val highlightAlpha: Float,
    val direction: Direction,
    val dropOff: Float,
    val intensity: Float,
    val tilt: Float,
    val durationMs: Int,
    val delay: Int,
    val repeatMode: RepeatMode,
    clock: AnimationClockObservable
) : ShimmerEffect {

    private val animation: TransitionAnimation<ShimmerTransition.State>
    private var animationPulse by mutableStateOf(0L)

    private var translateHeight = 0f
    private var translateWidth = 0f
    private val tiltTan = tan(Math.toRadians(tilt.toDouble())).toFloat()
    private val shaderColors = listOf(
        Color.Unset.copy(alpha = baseAlpha),
        Color.Unset.copy(alpha = highlightAlpha),
        Color.Unset.copy(alpha = highlightAlpha),
        Color.Unset.copy(alpha = baseAlpha)
    )

    private val colorStops: List<Float> = listOf(
        ((1f - intensity - dropOff) / 2f).coerceIn(0f, 1f),
        ((1f - intensity - 0.001f) / 2f).coerceIn(0f, 1f),
        ((1f + intensity + 0.001f) / 2f).coerceIn(0f, 1f),
        ((1f + intensity + dropOff) / 2f).coerceIn(0f, 1f)
    )

    private val paint = Paint().apply {
        isAntiAlias = true
        style = PaintingStyle.Fill
        blendMode = BlendMode.DstIn
    }

    init {
        animation = ShimmerTransition.definition(
            durationMs = durationMs,
            delay = delay
        ).createAnimation(clock)

        animation.onUpdate = {
            animationPulse++
        }

        animation.onStateChangeFinished = { state ->
            if (state == ShimmerTransition.State.Begin) animation.toState(ShimmerTransition.State.End)
            else if (state == ShimmerTransition.State.End)
                if (repeatMode == RepeatMode.RESTART) {
                    animation.toState(ShimmerTransition.State.Reset)
                } else if (repeatMode == RepeatMode.REVERSE) {
                    animation.toState(ShimmerTransition.State.Begin)
                }
        }
        animation.toState(ShimmerTransition.State.End)
    }

    override fun draw(canvas: Canvas, size: Size) {
        animationPulse // model read so we will be redrawn with the next animation values

        val progress = animation[ShimmerTransition.progress]

        val (dx, dy) = when (direction) {
            Direction.LeftToRight -> Pair(
                offset(-translateWidth, translateWidth, progress),
                0f
            )
            Direction.RightToLeft -> Pair(
                offset(translateWidth, -translateWidth, progress),
                0f
            )

            Direction.TopToBottom -> Pair(
                0f,
                offset(-translateHeight, translateHeight, progress)
            )


            Direction.BottomToTop -> Pair(
                0f,
                offset(translateHeight, -translateHeight, progress)
            )

        }
        paint.shader?.transform {
            reset()
            postRotate(tilt, size.width / 2f, size.height / 2f)
            postTranslate(dx, dy)
        }

        canvas.drawRect(
            rect = Rect(0f, 0f, size.width, size.height),
            paint = paint
        )
    }


    override fun updateSize(size: Size) {
        translateHeight = size.height + tiltTan * size.width
        translateWidth = size.width + tiltTan * size.height


        val toOffset = when (direction) {
            Direction.RightToLeft, Direction.LeftToRight -> Offset(size.width, 0f)
            else -> Offset(0f, size.height)
        }

        paint.shader = LinearGradientShader(
            Offset(0f, 0f),
            toOffset,
            shaderColors,
            colorStops

        )
    }

    private fun offset(start: Float, end: Float, percent: Float): Float {
        //  percent = 0 => start
        //  percent = 1 => end
        return start + (end - start) * percent
    }

}

private object ShimmerTransition {

    enum class State {
        Begin,
        End,
        Reset
    }

    val progress = FloatPropKey()

    fun definition(
        durationMs: Int,
        delay: Int
    ) = transitionDefinition<State> {

        state(State.Begin) {
            this[progress] = 0f
        }

        state(State.End) {
            this[progress] = 1f
        }

        state(State.Reset) {
            this[progress] = 0f
        }

        snapTransition(State.End to State.Reset, nextState = State.Begin)

        transition(fromState = State.Begin, toState = State.End) {
            progress using tween (
                durationMillis = durationMs,
                delayMillis = delay
            )
        }

        transition(fromState = State.End, toState = State.Begin) {
            progress using tween<Float>(
                durationMillis = durationMs,
                delayMillis = delay
            )
        }

    }

}
