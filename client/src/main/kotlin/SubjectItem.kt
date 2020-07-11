
import com.andb.apps.aspen.models.Subject
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.div
import styled.css
import styled.styledDiv
import styled.styledP
import ui.common.MaterialIcon

class SubjectItem : RComponent<SubjectProps, RState>() {
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
                        backgroundColor = Color("#" + props.subject.config.color.toUInt().toString(16).substring(2))
                        width = 64.px
                        height = 64.px
                        margin(right = 24.px)
                        display = Display.flex
                        justifyContent = JustifyContent.center
                        alignItems = Align.center
                    }
                    MaterialIcon("school"){
                        width = 32.px
                        height = 32.px
                    }
                }
                div {
                    styledP {
                        css {
                            fontWeight = FontWeight.w600
                        }
                        +props.subject.name
                    }
                    styledP { +props.subject.teacher }
                }
            }

            styledP(){
                css {
                    fontWeight = FontWeight.w600
                }
                +props.subject.termGrades(props.term).grade.toString()
            }
        }
    }
}

interface SubjectProps : TermProps{
    var subject: Subject
}

fun RBuilder.SubjectItem(subject: Subject, term: Int){
    child(SubjectItem::class){
        attrs {
            this.subject = subject
            this.term = term
        }
    }
}