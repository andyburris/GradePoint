package ui.home

import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import styled.css
import styled.styledDiv
import ui.dp
import util.shimmer

class LoadingSubjectItem : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
            }

            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                    alignItems = Align.center
                }
                styledDiv {
                    css {
                        borderRadius = 50.pct
                        width = 64.dp
                        height = 64.dp
                        margin(right = 24.dp)
                        display = Display.flex
                        justifyContent = JustifyContent.center
                        alignItems = Align.center
                        shimmer()
                    }
                }
                div {
                    styledDiv {
                        css {
                            height = 16.dp
                            width = 160.dp
                            marginBottom = 4.dp
                            shimmer()
                        }
                    }
                    styledDiv {
                        css {
                            height = 16.dp
                            width = 120.dp
                            shimmer()
                        }
                    }
                }
            }

            styledDiv {
                css {
                    height = 16.dp
                    width = 60.dp
                    shimmer()
                }
            }
        }

    }
}

fun RBuilder.LoadingSubjectItem(){
    child(LoadingSubjectItem::class){}
}