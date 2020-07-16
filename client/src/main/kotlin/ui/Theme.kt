package ui

import kotlinx.css.*
import react.RBuilder
import styled.css
import styled.styledP
import util.clamp

object Theme {
    val Primary = Color("#388E3C")
}

fun CSSBuilder.bold() {
    fontWeight = FontWeight.w600
    fontSize = 20.sp
}

fun CSSBuilder.secondary() {
    fontWeight = FontWeight.normal
    color = rgba(0, 0, 0, 0.5)
    fontSize = 20.sp
}

fun CSSBuilder.header() {
    fontWeight = FontWeight.w600
    fontSize = 72.sp
}

fun CSSBuilder.h3(){
    fontWeight = FontWeight.w600
    fontSize = 36.sp
}
val Number.sp: LinearDimension get() = clamp(min = this.px * 0.75, between = this.vmin / 12, max = this.px * 2)
val Number.dp: LinearDimension get() = clamp(min = this.px * 0.75, between = this.vmin / 12, max = this.px * 2)

enum class TextVarient{
    Bold, Secondary, Header, H3
}
fun RBuilder.Text(text: String, variant: TextVarient, cssBuilder: CSSBuilder.() -> Unit = {}){
    styledP {
        css {
            when (variant){
                TextVarient.Bold -> bold()
                TextVarient.Secondary -> secondary()
                TextVarient.Header -> header()
                TextVarient.H3 -> h3()
            }
            cssBuilder.invoke(this)
        }
        +text
    }
}