package ui.home

import kotlinx.css.*
import kotlinx.css.properties.IterationCount
import kotlinx.css.properties.animation
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import styled.*
import ui.common.MaterialIcon

class LoadingSubjectItem : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
                kotlinx.css.div {
                    background = "linearGradient(to right, #eff1f3 4%, #e2e2e2 25%, #eff1f3 36%);"
                    backgroundSize = "1000px, 100%"
                    animation(duration = 2.s, iterationCount = IterationCount.infinite){
                        0 { backgroundPosition = "-1000px 0" }
                        100 { backgroundPosition = "1000px 0"}
                    }
                }
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
                    }
                }
                div {
                    styledDiv {
                        css {
                            height = 16.px
                            width = 160.px
                        }
                    }
                    styledDiv {
                        css {
                            height = 16.px
                            width = 120.px
                        }
                    }
                }
            }

            styledDiv {
                css {
                    height = 16.px
                    width = 60.px
                }
            }
        }

    }
}
fun RBuilder.LoadingSubjectItem(){
    child(LoadingSubjectItem::class){}
}