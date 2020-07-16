@file:JsModule("react-flip-toolkit")

package ui.common

import react.RClass
import react.RProps

@JsName("Flipper")
external val Flipper : RClass<FlipperProps>

external interface FlipperProps : RProps{
    var flipKey: Any
    var spring: dynamic
    var applyTransformOrigin: Boolean
    var element: String
    var className: String
    var staggerConfig: dynamic
    var debug: Boolean
    var onComplete: () -> Unit
}

@JsName("Flipped")
external val Flipped : RClass<FlippedProps>

external interface FlippedProps : RProps {
    var flipId: String
    var inverseFlipId: String
    var transformOrigin: String
    var spring: dynamic
    var stagger: dynamic
    var delayUntil: String
    var translate: String
    var scale: String
    var opacity: String
}