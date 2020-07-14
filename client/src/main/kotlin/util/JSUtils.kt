package util

import kotlinx.css.*
import kotlinx.css.properties.IterationCount
import kotlinx.css.properties.s
import kotlinx.html.DIV
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import react.RBuilder
import styled.StyledDOMBuilder
import styled.animation
import styled.css
import styled.styledDiv

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

fun CSSBuilder.shimmer(){
    background = "linear-gradient(to right, #eff1f3 4%, #e2e2e2 25%, #eff1f3 36%);"
    backgroundSize = "1000px, 100%"
    animation(duration = 2.s, iterationCount = IterationCount.infinite){
        0 { backgroundPosition = "-1000px 0" }
        100 { backgroundPosition = "1000px 0"}
    }
}

fun CSSBuilder.displayFlex(direction: FlexDirection = FlexDirection.inherit, justifyContent: JustifyContent = JustifyContent.inherit, alignItems: Align = Align.auto){
    display = Display.flex
    flexDirection = direction
    this.justifyContent = justifyContent
    this.alignItems = alignItems
}

fun RBuilder.flexbox(direction: FlexDirection = FlexDirection.inherit, justifyContent: JustifyContent = JustifyContent.inherit, alignItems: Align = Align.auto, otherCSS: CSSBuilder.() -> Unit = {}, children: StyledDOMBuilder<DIV>.() -> Unit){
    styledDiv {
        css {
            displayFlex(direction, justifyContent, alignItems)
            otherCSS.invoke(this)
        }
        children.invoke(this)
    }
}