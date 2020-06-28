package com.andb.apps.aspen.util

import androidx.ui.graphics.Color
import androidx.ui.material.ColorPalette
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette
import androidx.ui.unit.Bounds
import androidx.ui.unit.Density
import androidx.ui.unit.PxBounds
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