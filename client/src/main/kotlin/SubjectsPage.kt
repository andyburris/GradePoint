
import com.andb.apps.aspen.models.Subject
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.h1
import styled.css
import styled.styledDiv
import ui.common.OutlinedButton

class SubjectsPage : RComponent<SubjectsProps, SubjectsState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                width = 100.vw
                padding(horizontal = 64.px)
                boxSizing = BoxSizing.borderBox
            }

            styledDiv {
                css {
                    padding(top = 64.px, bottom = 32.px)
                    width = 100.pct
                    boxSizing = BoxSizing.borderBox
                    display = Display.flex
                    justifyContent = JustifyContent.spaceBetween
                }

                h1 { +"Classes" }
                OutlinedButton("Term ${props.term}", onClick = {}) {}
            }

            for (subject in props.subjects){
                styledDiv {
                    css {
                        marginBottom = 16.px
                    }
                    SubjectItem(subject, props.term)
                }
            }
        }
    }

}

interface SubjectsProps : TermProps {
    var subjects: List<Subject>
}

interface SubjectsState : RState {
    var termExpanded : Boolean
}

fun RBuilder.subjectsPage(subjects: List<Subject>, term: Int, handler: ActionHandler){
    child(SubjectsPage::class){
        attrs {
            this.subjects = subjects
            this.term = term
            this.handler = handler
        }
    }
}