package com.andb.apps.aspen.ui.common.color

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

data class HSB(val hue: Float, val saturation: Float, val brightness: Float, val alpha: Float = 1f)

fun Color.toHSB(): HSB {
    val hsvOut = floatArrayOf(0f, 0f, 0f)
    android.graphics.Color.colorToHSV(toArgb(), hsvOut)
    return HSB(hsvOut[0]/360, hsvOut[1], hsvOut[2], alpha)
}

fun HSB.toColor(): Color {
    val colorInt = android.graphics.Color.HSVToColor((alpha * 255).toInt(), floatArrayOf(hue * 360, saturation, brightness))
    println("hsv = $this, color = ${Color(colorInt)}")
    return Color(colorInt)
}