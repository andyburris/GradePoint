package ui.home

import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import styled.css
import styled.styledDiv
import ui.sp
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
                        width = 64.px
                        height = 64.px
                        margin(right = 24.px)
                        display = Display.flex
                        justifyContent = JustifyContent.center
                        alignItems = Align.center
                        shimmer()
                    }
                }
                div {
                    styledDiv {
                        css {
                            height = 16.sp
                            width = 160.px
                            marginBottom = 4.px
                            shimmer()
                        }
                    }
                    styledDiv {
                        css {
                            height = 16.sp
                            width = 120.px
                            shimmer()
                        }
                    }
                }
            }

            styledDiv {
                css {
                    height = 16.px
                    width = 60.px
                    shimmer()
                }
            }
        }

    }
}

fun RBuilder.LoadingSubjectItem(){
    child(LoadingSubjectItem::class){}
}