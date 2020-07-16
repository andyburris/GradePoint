package ui.subject

import com.andb.apps.aspen.models.Assignment
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.tbody
import react.dom.td
import react.dom.tr
import styled.css
import styled.styledDiv
import styled.styledTable
import styled.styledTh
import ui.Text
import ui.TextVarient
import ui.Theme
import ui.common.AssignmentItem
import ui.dp

external interface AssignmentTableProps : RProps {
    var assignments: List<Assignment>
}

private val AssignmentTable = functionalComponent<AssignmentTableProps> { props ->

    val (expanded, setExpanded) = useState("")

    styledTable {
        css {
            borderSpacing = 32.dp
            margin(all = (-32).dp)
            width = 100.pct
        }

        tbody {
            AssignmentTableHeader()
            for (assignment in props.assignments){
                tr {
                    attrs.onClickFunction = { setExpanded.invoke(assignment.id) }
                    AssignmentItem(assignment)
                }

                if (assignment.id == expanded){
                    ExpandedAssignment(assignment)
                }
            }
        }
    }
}

private fun RBuilder.ExpandedAssignment(assignment: Assignment){
    tr {
        td {
            attrs {
                colSpan = "3"
            }

            styledDiv {
                css {
                    margin(all = (-32).dp)
                    padding(all = 32.dp)
                }
            }
        }
    }
}

private fun RBuilder.AssignmentTableHeader(){
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
}

fun RBuilder.AssignmentTable(assignments: List<Assignment>){
    child(AssignmentTable){
        attrs {
            this.assignments = assignments
        }
    }
}