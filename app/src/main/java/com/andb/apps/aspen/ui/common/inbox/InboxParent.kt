package com.andb.apps.aspen.ui.common.inbox

import androidx.animation.AnimatedFloat
import androidx.animation.AnimationBuilder
import androidx.animation.AnimationEndReason
import androidx.animation.TweenBuilder
import androidx.compose.*
import androidx.ui.animation.animatedFloat
import androidx.ui.core.*
import androidx.ui.layout.Stack
import androidx.ui.layout.height
import androidx.ui.layout.offset
import androidx.ui.unit.Bounds
import androidx.ui.unit.height
import androidx.ui.unit.width
import com.andb.apps.aspen.util.toDpBounds

private class InboxState<T> {
    var current: T? = null
    var stack = mutableListOf<InboxAnimationItem<T>>()
    var invalidate: () -> Unit = { }
}

private data class InboxAnimationItem<T>(
    val key: T,
    val transition: InboxTransition
)

private typealias InboxTransition = @Composable() (children: @Composable() () -> Unit) -> Unit
val SnapTransition: InboxTransition = { children -> Stack { children() } }

@Composable
fun <T> InboxParent(
    current: T,
    tag: (T) -> String,
    areEquivalent: (old: T?, new: T) -> Boolean = ReferentiallyEqual,
    animation: AnimationBuilder<Float> = TweenBuilder(),
    children: @Composable() (T) -> Unit
) {
    InboxControllerProvider {
        val state = remember { InboxState<T>() }
        if (!areEquivalent.invoke(state.current, current)) {
            state.current = current
            val keys = state.stack.map { it.key }.toMutableList()
            if (!keys.contains(current)) {
                keys.add(current)
            }
            val controller = InboxAnimationControllerAmbient.current
            if (keys.none { controller.hasTag(tag.invoke(it)) }){
                state.stack = mutableListOf(InboxAnimationItem(key = current, transition = SnapTransition))
                state.invalidate()
            } else {
                state.stack.clear()
                keys.mapTo(state.stack) { key ->
                    InboxAnimationItem(key) { children ->
                        val item = InboxAnimationControllerAmbient.current.animationItems[tag.invoke(key)]
                        if (item == null){ // if no item to expand from, show page without animation
                            Stack { children() }
                            return@InboxAnimationItem
                        }

                        val pageBounds = state<Bounds?> { null }
                        if (pageBounds.value != null) {
                            val scale = animatedScale(
                                animation = animation,
                                visible = key == current,
                                onAnimationFinish = {
                                    if (key == state.current) {
                                        // leave only the current in the list
                                        state.stack.removeAll { it.key != state.current }
                                        state.invalidate()
                                    }
                                }
                            )

                            val left = item.bounds.left + (pageBounds.value!!.left - item.bounds.left) * scale.value
                            val top = item.bounds.top + (pageBounds.value!!.top - item.bounds.top) * scale.value
                            val width = item.bounds.width + (pageBounds.value!!.width - item.bounds.width) * scale.value
                            println("width - itemWidth = ${item.bounds.width}, pageWidth = ${pageBounds.value?.width}")
                            val height = item.bounds.height + (pageBounds.value!!.height - item.bounds.height) * scale.value
                            Stack(Modifier.offset(y = top).height(height =  height)) {
                                children()
                            }
                        } else {
                            val density = DensityAmbient.current
                            Stack(
                                modifier = Modifier.onChildPositioned {
                                    if (pageBounds.value == null){
                                        pageBounds.value = it.globalBounds.toDpBounds(density)
                                    }
                                }.drawLayer(alpha = 0f),
                                children = { children() }
                            )
                        }
                    }
                }
            }
        }
        Stack {
            state.invalidate = invalidate
            state.stack.forEach { inboxAnimationItem ->
                key(inboxAnimationItem.key) {
                    inboxAnimationItem.transition {
                        children(inboxAnimationItem.key)
                    }
                }
            }
        }
    }
}

@Composable
fun <T> InboxParent2(
    new: T,
    tag: (T) -> String,
    areEquivalent: (old: T?, new: T) -> Boolean = ReferentiallyEqual,
    animation: AnimationBuilder<Float> = TweenBuilder(),
    children: @Composable() (T) -> Unit
) {
    InboxControllerProvider {
        val state = remember { InboxState<T>() }

        if (!areEquivalent(state.current, new)){

        }


        Stack {
            children(new)
        }
    }
}

private fun InboxAnimationController.hasTag(tag: String): Boolean {
    return animationItems.containsKey(tag)
}

@Composable
private fun InboxControllerProvider(children: @Composable() () -> Unit){
    val controller = remember { InboxAnimationController() }
    Providers(InboxAnimationControllerAmbient provides controller, children = children)
}

@Composable
private fun animatedScale(
    animation: AnimationBuilder<Float>,
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
