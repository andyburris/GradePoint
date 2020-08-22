package com.andb.apps.aspen.ui.common.inbox

import androidx.compose.runtime.ambientOf
import androidx.compose.ui.unit.Bounds

class InboxAnimationController(){
    val animationItems = mutableMapOf<String, InboxAnimationConfig>()
}

class InboxAnimationConfig(val bounds: Bounds)

val InboxAnimationControllerAmbient = ambientOf<InboxAnimationController>()