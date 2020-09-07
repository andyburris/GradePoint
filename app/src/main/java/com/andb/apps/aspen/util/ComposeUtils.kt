package com.andb.apps.aspen.util

import androidx.compose.ui.graphics.Color
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.staticAmbientOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Bounds
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.PxBounds
import androidx.core.graphics.ColorUtils
import com.andb.apps.aspen.AndroidSettings
import com.andb.apps.aspen.state.UserAction

class ActionHandler(val handle: (UserAction) -> Boolean)
val SettingsAmbient = staticAmbientOf<AndroidSettings>()

fun PxBounds.toDpBounds(density: Density) = with(density) {
    Bounds(left.toDp(), top.toDp(), right.toDp(), bottom.toDp())
}

fun Colors.copy(
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
): Colors {
    return if (isLight){
        lightColors(primary, primaryVariant, secondary, secondaryVariant, background, surface, error, onPrimary, onSecondary, onBackground, onSurface, onError)
    } else {
        darkColors(primary, primaryVariant, secondary, background, surface, error, onPrimary, onSecondary, onBackground, onSurface, onError)
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
