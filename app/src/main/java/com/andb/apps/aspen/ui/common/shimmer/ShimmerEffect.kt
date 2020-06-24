package com.andb.apps.aspen.ui.common.shimmer

import androidx.ui.geometry.Size
import androidx.ui.graphics.Canvas


interface ShimmerEffect {

    fun draw(canvas: Canvas, size: Size)

    fun updateSize(size: Size)

}