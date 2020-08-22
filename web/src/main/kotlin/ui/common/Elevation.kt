package ui.common

import kotlinx.css.CSSBuilder
import kotlinx.css.boxShadow
import kotlinx.css.properties.BoxShadow
import kotlinx.css.properties.BoxShadows
import kotlinx.css.px
import kotlinx.css.rgba

fun CSSBuilder.elevation(dp: Int){
    boxShadow = BoxShadows().apply {
        this += BoxShadow(false, offsetX = 0.px, offsetY = 0.px, blurRadius = 0.px, spreadRadius = 0.px, color = rgba(0, 0, 0, 0.2))
    }
}