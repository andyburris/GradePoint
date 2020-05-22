package com.andb.apps.aspen.ui.common.shimmer

import androidx.ui.graphics.Canvas
import androidx.ui.unit.PxSize


interface ShimmerEffect {

    fun draw(canvas: Canvas, size: PxSize)

    fun updateSize(size: PxSize)

}