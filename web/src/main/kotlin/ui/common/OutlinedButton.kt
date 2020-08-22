package ui.common

import kotlinx.css.*
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import styled.css
import styled.styledButton
import ui.Theme
import ui.dp

fun RBuilder.OutlinedButton(text: String, onClick: (Event) -> Unit, cssBuilder: CSSBuilder.() -> Unit){
    styledButton {
        css {
            padding(all = 24.dp)
            position = Position.relative
            borderRadius = 0.dp
            borderWidth = 2.dp
            border = "solid ${Theme.Primary}"
            backgroundColor = Color.transparent
            fontSize = 1.5.rem
            color = Theme.Primary
            fontWeight = FontWeight.w600
            cursor = Cursor.pointer
            textTransform = TextTransform.uppercase
            overflow = Overflow.hidden
            ":hover"{
                color = Color.white
            }
            ":before" {
                content = QuotedString("")
                position = Position.absolute
                backgroundColor = Theme.Primary
                bottom = 0.dp
                top = 0.dp
                left = 0.dp
                right = 100.pct
                zIndex = -1
                transition("right", 100.ms)
            }
            ":hover:before"{
                right = 0.pct
            }
            cssBuilder.invoke(this)

        }

        attrs {
            this.onClickFunction = onClick
        }

        +text
    }
}