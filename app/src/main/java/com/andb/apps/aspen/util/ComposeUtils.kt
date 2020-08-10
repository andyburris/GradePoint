package com.andb.apps.aspen.util

import androidx.compose.ui.graphics.Color
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.unit.Bounds
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.PxBounds
import com.andb.apps.aspen.state.UserAction

class ActionHandler(val handle: (UserAction) -> Boolean)

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