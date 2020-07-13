package ui.home
import ActionHandler
import TermProps
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.state.UserAction
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.h1
import styled.css
import styled.styledDiv
import ui.common.OutlinedButton

class SubjectListPage : RComponent<SubjectsProps, SubjectsState>() {
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

            if (props.subjects.any { it.hasTerm(props.term) }){
                for (subject in props.subjects){
                    styledDiv {
                        css {
                            marginBottom = 16.px
                        }
                        SubjectItem(
                            subject = subject,
                            term = props.term,
                            onClick = {
                                val screen = Screen.Subject(subject, props.term)
                                props.handler(UserAction.OpenScreen(screen))
                            }
                        )
                    }
                }
            } else {
                repeat(8) {
                    styledDiv {
                        css { marginBottom = 16.px }
                        LoadingSubjectItem()
                    }
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

fun RBuilder.SubjectListPage(subjects: List<Subject>, term: Int, handler: ActionHandler){
    child(SubjectListPage::class){
        attrs {
            this.subjects = subjects
            this.term = term
            this.handler = handler
        }
    }
}