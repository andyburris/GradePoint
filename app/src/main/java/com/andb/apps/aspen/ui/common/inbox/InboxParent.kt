package com.andb.apps.aspen.ui.common.inbox

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimatedFloat
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.layout.globalBounds
import androidx.compose.ui.onChildPositioned
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.Bounds
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import com.andb.apps.aspen.util.toDpBounds


private class InboxState<T> {
    var current: T? = null
    var stack = mutableListOf<InboxAnimationItem<T>>()
    var invalidate: () -> Unit = { }
}

private data class InboxAnimationItem<T>(
    val key: T,
    val tag: String,
    val transition: InboxTransition = SnapTransition
) {
    override fun toString(): String {
        return "InboxAnimationItem(key = ${key.toString().takeWhile { it != '(' }}, tag = $tag, transition = $transition"
    }
}

private typealias InboxTransition = @Composable() (children: @Composable() () -> Unit) -> Unit
val SnapTransition: InboxTransition = { children ->  children() }

@Composable
fun <T> InboxParent(
    new: T,
    currentTag: String,
    areEquivalent: (old: T?, new: T) -> Boolean = ReferentiallyEqual,
    animation: AnimationSpec<Float> = TweenSpec(),
    children: @Composable() (T) -> Unit
) {
    InboxControllerProvider {
        val state = remember { InboxState<T>() }
        if (!areEquivalent.invoke(state.current, new)) {
            state.current = new
            if (new !in state.stack.map { it.key }) {
                state.stack.add(InboxAnimationItem(new, currentTag))
            }
            val controller = InboxAnimationControllerAmbient.current
            if (state.stack.none { controller.hasTag(it.tag) }){
                state.stack = mutableListOf(InboxAnimationItem(key = new, tag = currentTag, transition = SnapTransition))
                state.invalidate()
            } else {
                state.stack.clear()
                state.stack = state.stack.map { animationItem ->
                    InboxAnimationItem(animationItem.key, animationItem.tag) { children ->
                        val item = InboxAnimationControllerAmbient.current.animationItems[animationItem.tag]
                        if (item == null){ // if no item to expand from, show page without animation
                            Stack { children() }
                            return@InboxAnimationItem
                        }

                        val pageBounds = state<Bounds?> { null }
                        if (pageBounds.value != null) {
                            val scale = animatedScale(
                                animation = animation,
                                visible = animationItem.key == new,
                                onAnimationFinish = {
                                    if (animationItem.key == state.current) {
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
                }.toMutableList()
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
    newState: T,
    newTag: String,
    areEquivalent: (old: T?, new: T) -> Boolean = ReferentiallyEqual,
    animation: AnimationSpec<Float> = TweenSpec(),
    children: @Composable() (T) -> Unit
) {
    InboxControllerProvider {
        val state = remember { InboxState<T>() }

        if (newState != state.current){
            state.current = newState
            state.stack.retainAll { !areEquivalent(it.key, newState) }
            state.stack.add(InboxAnimationItem(newState, newTag))
            println("new state - stack = ${state.stack}")
        }


        Stack {
            //state.invalidate = invalidate
            //state.stack.forEach { animationItem ->
            //if (state.current != null){
                //key(animationItem.key) {
                    children(newState)
                //}
            //}
            //}
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
