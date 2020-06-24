package com.andb.apps.aspen.util

import androidx.ui.unit.Bounds
import androidx.ui.unit.Density
import androidx.ui.unit.PxBounds
import com.andb.apps.aspen.state.UserAction

class ActionHandler(val handle: (UserAction) -> Boolean)

fun PxBounds.toDpBounds(density: Density) = with(density) {
    Bounds(left.toDp(), top.toDp(), right.toDp(), bottom.toDp())
}