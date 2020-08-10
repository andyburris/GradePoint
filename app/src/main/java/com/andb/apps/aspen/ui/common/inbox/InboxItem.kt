package com.andb.apps.aspen.ui.common.inbox

import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.globalBounds
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.andb.apps.aspen.util.toDpBounds

fun Modifier.inboxItem(id: String): Modifier = composed {
    val controller = InboxAnimationControllerAmbient.current
    if (controller.animationItems.containsKey(id)){
        return@composed this //+ InboxItemModifier()
    } else {
        val density = DensityAmbient.current
        return@composed this + onPositioned {
            val bounds = it.globalBounds.toDpBounds(density)
            controller.animationItems[id] = InboxAnimationConfig(bounds)
        }
    }
}

private class InboxItemModifier(val height: Dp) : LayoutModifier{
    override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureScope.MeasureResult {
        val placeable = measurable.measure(constraints.copy(maxHeight = height.toIntPx()))
        return layout(placeable.width, placeable.height){
            placeable.place(Offset.Zero)
        }
    }
}