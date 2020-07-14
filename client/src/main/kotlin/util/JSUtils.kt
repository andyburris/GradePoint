package util

import kotlinx.css.CSSBuilder
import kotlinx.css.LinearDimension
import kotlinx.css.fontSize
import kotlinx.css.px
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event

val Event.targetInputValue: String
    get() = (target as? HTMLInputElement)?.value ?: (target as? HTMLTextAreaElement)?.value ?: ""

fun CSSBuilder.fluidType(
    minSize: LinearDimension = 16.px,
    maxSize: LinearDimension = 16.px,
    rangeMin: LinearDimension = 0.px,
    rangeMax: LinearDimension = 1000.px
) {
    fontSize = minSize
    media("(min-width: ${rangeMin.value})"){
        fontSize = LinearDimension("calc($minSize + (${maxSize.number} - ${minSize.number}) * ((100vw - $rangeMin) / (${rangeMax.number} - ${rangeMin.number})))")
    }
    media("(min-width: ${rangeMax.value})"){
        fontSize = maxSize
    }
}

fun clamp(min: LinearDimension, between: LinearDimension, max: LinearDimension): LinearDimension{
    return LinearDimension("clamp($min, $between, $max)")
}

private operator fun Number.times(linearDimension: LinearDimension): LinearDimension = linearDimension.times(this)
private val LinearDimension.number: Double get() = value.takeWhile { it in '0'..'9' }.toDouble()