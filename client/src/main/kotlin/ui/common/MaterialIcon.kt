package ui.common

import kotlinx.css.*
import react.RBuilder
import react.dom.i
import styled.css
import styled.styledDiv

fun RBuilder.MaterialIcon(name: String, size: LinearDimension = 24.px, color: Color = rgba(0, 0, 0, 0.5), cssBuilder: CSSBuilder.() -> Unit = {}){
    styledDiv {
        css {
            kotlinx.css.i {
                fontSize = size
                this.color = color
            }
            height = size
            cssBuilder.invoke(this)
        }
        i(classes = "material-icons"){
            +name
        }
    }
}