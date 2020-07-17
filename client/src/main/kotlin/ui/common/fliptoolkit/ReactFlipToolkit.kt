@file:JsModule("react-flip-toolkit")

package ui.common.fliptoolkit

import org.w3c.dom.Element
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
    var onAppear: (element: Element, index: Int, decisionData: DecisionData) -> Unit
    var onStart: (element: Element, decisionData: DecisionData) -> Unit
    var onStartImmediate: (element: Element, decisionData: DecisionData) -> Unit
    var onComplete: (element: Element, decisionData: DecisionData) -> Unit
    var onExit: (element: Element, index: Int, removeElement: () -> Unit, decisionData: DecisionData) -> Unit
}

external interface DecisionData {
    var previousDecision: dynamic
    var currentDecision: dynamic
}

@JsName("spring")
internal external val spring : (SpringOptions) -> Unit

internal external interface SpringOptions {
    var config: String
    var values: dynamic
    var onUpdate: (dynamic) -> Unit
    var delay: Number
    var onComplete: () -> Unit
}

