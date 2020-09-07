package com.andb.apps.aspen.ui.common

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimatedFloat
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Stack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.util.fastForEach

/**
 * [Crossfade] allows to switch between two layouts with a crossfade animation.
 *
 * @sample androidx.compose.animation.samples.CrossfadeSample
 *
 * @param current is a key representing your current layout state. every time you change a key
 * the animation will be triggered. The [children] called with the old key will be faded out while
 * the [children] called with the new key will be faded in.
 * @param modifier Modifier to be applied to the animation container.
 * @param animation the [AnimationSpec] to configure the animation.
 */
@Composable
fun <T> BetterCrossfade(
    current: T,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = tween(),
    animateIf: (T?, T) -> Boolean = { old, new -> old != new },
    children: @Composable (T) -> Unit
) {
    val state = remember { CrossfadeState<T>() }
    val isFirst = state.current as? T == null
    println("isFirst = $isFirst, state.current = ${state.current}")
    if (isFirst || animateIf(state.current as? T, current)) {
        state.current = current
        val keys = state.items.map { it.key }.toMutableList()
        if (!keys.contains(current)) {
            keys.add(current)
        }
        state.items.clear()
        keys.mapTo(state.items) { key ->
            CrossfadeAnimationItem(key) { children ->
                val opacity = animatedOpacity(
                    animation = animation,
                    visible = key == current,
                    onAnimationFinish = {
                        if (key == state.current) {
                            // leave only the current in the list
                            state.items.removeAll { it.key != state.current }
                            state.invalidate()
                        }
                    }
                )
                Stack(Modifier.drawOpacity(opacity.value)) {
                    children()
                }
            }
        }
    }
    Stack(modifier) {
        state.invalidate = invalidate
        state.items.fastForEach { (item, opacity) ->
            key(item) {
                opacity {
                    children(item)
                }
            }
        }
    }
}

private class CrossfadeState<T> {
    // we use Any here as something which will not be equals to the real initial value
    var current: Any? = Any()
    var items = mutableListOf<CrossfadeAnimationItem<T>>()
    var invalidate: () -> Unit = { }
}

private data class CrossfadeAnimationItem<T>(
    val key: T,
    val transition: CrossfadeTransition
)

private typealias CrossfadeTransition = @Composable (children: @Composable () -> Unit) -> Unit

@Composable
private fun animatedOpacity(
    animation: AnimationSpec<Float>,
    visible: Boolean,
    onAnimationFinish: () -> Unit = {}
): AnimatedFloat {
    val animatedFloat = animatedFloat(if (!visible) 1f else 0f)
    onCommit(visible) {
        animatedFloat.animateTo(
            if (visible) 1f else 0f,
            anim = animation,
            onEnd = { reason, _ ->
                if (reason == AnimationEndReason.TargetReached) {
                    onAnimationFinish()
                }
            })
    }
    return animatedFloat
}
