package com.andb.apps.aspen.util

import androidx.core.graphics.ColorUtils
import androidx.ui.geometry.RRect
import androidx.ui.geometry.Radius
import androidx.ui.geometry.toRect
import androidx.ui.graphics.*
import androidx.ui.graphics.drawscope.DrawScope
import androidx.ui.material.ColorPalette
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette
import androidx.ui.unit.*
import com.andb.apps.aspen.state.UserAction

class ActionHandler(val handle: (UserAction) -> Boolean)

fun PxBounds.toDpBounds(density: Density) = with(density) {
    Bounds(left.toDp(), top.toDp(), right.toDp(), bottom.toDp())
}

fun ColorPalette.copy(
    primary: Color = this.primary,
    primaryVariant: Color = this.primaryVariant,
    secondary: Color = this.secondary,
    secondaryVariant: Color = this.secondaryVariant,
    background: Color = this.background,
    surface: Color = this.surface,
    error: Color = this.error,
    onPrimary: Color = this.onPrimary,
    onSecondary: Color = this.onSecondary,
    onBackground: Color = this.onBackground,
    onSurface: Color = this.onSurface,
    onError: Color = this.onError,
    isLight: Boolean = this.isLight
): ColorPalette {
    return if (isLight){
        lightColorPalette(primary, primaryVariant, secondary, secondaryVariant, background, surface, error, onPrimary, onSecondary, onBackground, onSurface, onError)
    } else {
        darkColorPalette(primary, primaryVariant, secondary, background, surface, error, onPrimary, onSecondary, onBackground, onSurface, onError)
    }
}

data class HSB(val hue: Float, val saturation: Float, val brightness: Float)

fun Color.toHSB(): HSB {
    val hsvOut = floatArrayOf(0f, 0f, 0f)
    android.graphics.Color.RGBToHSV((red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt(), hsvOut)
    return HSB(hsvOut[0]/360, hsvOut[1], hsvOut[2])
}

fun Color.toHSL(): Triple<Float, Float, Float> = this.toArgb().toHSL()

fun Int.toHSL(): Triple<Float, Float, Float> {
    val hslOut = floatArrayOf(0f, 0f, 0f)
    ColorUtils.colorToHSL(this, hslOut)
    return Triple(hslOut[0]/360, hslOut[1], hslOut[2])
}

fun HSB.toColor(): Color {
    val colorInt = android.graphics.Color.HSVToColor(floatArrayOf(hue * 360, saturation, brightness))
    println("hsv = $this, color = ${Color(colorInt)}")
    return Color(colorInt)
}
