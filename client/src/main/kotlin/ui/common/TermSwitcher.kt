package ui.common

import TermProps
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RState
import react.setState
import styled.css
import styled.styledDiv
import ui.Text
import ui.TextVarient
import ui.Theme

class TermSwitcher : RComponent<SwitcherProps, SwitcherState>(){
    override fun RBuilder.render() {
        styledDiv {
            css {
                padding(horizontal = 16.px, vertical = 12.px)
                border = "solid ${Theme.Primary}"
                borderRadius = 0.px
                borderWidth = 2.px
                backgroundColor = Color.transparent
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                cursor = Cursor.pointer
            }

            attrs {
                onClickFunction = {
                    setState {
                        expanded = !expanded
                    }
                }
            }

            MaterialIcon("filter_list", color = Theme.Primary){
                marginRight = 16.px
            }

            Text("TERM", TextVarient.Bold){
                color = Theme.Primary
            }

            if (state.expanded){
                styledDiv {
                    css { width = 16.px }
                }
                TermItem(term = 1, selected = props.term == 1) { props.onSwitch.invoke(1) }
                TermItem(term = 2, selected = props.term == 2) { props.onSwitch.invoke(2) }
                TermItem(term = 3, selected = props.term == 3) { props.onSwitch.invoke(3) }
                TermItem(term = 4, selected = props.term == 4) { props.onSwitch.invoke(4) }
            }
        }
    }
}

private fun RBuilder.TermItem(term: Int, selected: Boolean, onClick: () -> Unit){
    styledDiv {
        css {
            width = 36.px
            height = 36.px
            borderRadius = 50.pct
            backgroundColor = if (selected) Theme.Primary else Color.transparent
            color = if (selected) Color.white else Theme.Primary
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = Align.center
        }

        attrs {
            onClickFunction = {
                it.stopPropagation()
                onClick.invoke()
            }
        }

        Text("$term", TextVarient.Bold)
    }
}

interface SwitcherProps : TermProps {
    var onSwitch: (Int) -> Unit
}

interface SwitcherState : RState {
    var expanded: Boolean
}

fun RBuilder.TermSwitcher(term: Int, onSwitch: (Int) -> Unit){
    child(TermSwitcher::class){
        attrs {
            this.term = term
            this.onSwitch = onSwitch
        }
    }
}