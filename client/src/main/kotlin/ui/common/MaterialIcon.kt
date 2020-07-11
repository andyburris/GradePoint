package ui.common

import kotlinx.css.*
import react.RBuilder
import react.dom.i
import styled.css
import styled.styledDiv
import styled.styledI

fun RBuilder.MaterialIcon(name: String, cssBuilder: CSSBuilder.() -> Unit){
    styledDiv {
        css {
            kotlinx.css.i {
                fontSize = 32.px
                color = rgba(0, 0, 0, 0.5)
            }
            cssBuilder.invoke(this)
        }
        i(classes = "material-icons"){
            +name
        }
    }
}