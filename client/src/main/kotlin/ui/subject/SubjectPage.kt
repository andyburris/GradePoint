package ui.subject

import ActionHandler
import ActionHandlerProps
import TermProps
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.state.UserAction
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.tr
import styled.css
import styled.styledDiv
import styled.styledTable
import styled.styledTh
import ui.Text
import ui.TextVarient
import ui.Theme
import ui.common.AssignmentItem
import ui.common.TermSwitcher
import util.displayFlex

class SubjectPage : RComponent<SubjectPageProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                width = 100.vw
                padding(horizontal = 64.px)
                boxSizing = BoxSizing.borderBox
            }

            styledDiv {
                css {
                    width = 100.pct
                    displayFlex(justifyContent = JustifyContent.spaceBetween, alignItems = Align.center)
                    margin(vertical = 64.px)
                }
                Text(props.subject.name, TextVarient.Header)
                TermSwitcher(props.term) { props.handler(UserAction.SwitchTerm(it)) }
            }

            AssignmentTable(props.subject.termGrades(props.term).assignments)
        }
    }
}

private fun RBuilder.AssignmentTable(assignments: List<Assignment>){
    styledTable {
        css {
            borderSpacing = 32.px
            margin(all = (-32).px)
        }
        tr {
            styledTh {
                css { textAlign = TextAlign.start }
                Text("ASSIGNMENT", TextVarient.Bold){
                    color = Theme.Primary
                }
            }
            styledTh {
                css { textAlign = TextAlign.end }
                Text("DUE", TextVarient.Bold){
                    color = rgba(0, 0, 0, 0.5)
                }
            }
            styledTh {
                css { textAlign = TextAlign.end }
                Text("GRADE", TextVarient.Bold){
                    color = rgba(0, 0, 0, 0.5)
                }
            }
        }
        for (assignment in assignments){
            tr {
                AssignmentItem(assignment)
            }
        }
    }
}

interface SubjectPageProps : TermProps, ActionHandlerProps{
    var subject: Subject
}

fun RBuilder.SubjectPage(subject: Subject, term: Int, handler: ActionHandler) {
    child(SubjectPage::class) {
        attrs {
            this.subject = subject
            this.term = term
            this.handler = handler
        }
    }
}