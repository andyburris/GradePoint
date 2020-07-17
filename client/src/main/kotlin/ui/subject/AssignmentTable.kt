package ui.subject

import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.SubjectGrade
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.div
import react.dom.tbody
import react.dom.td
import react.dom.tr
import styled.*
import ui.Text
import ui.TextVarient
import ui.Theme
import ui.common.AssignmentItem
import ui.common.MaterialIcon
import ui.common.fliptoolkit.animated
import ui.common.fliptoolkit.animatorBase
import ui.dp
import util.displayFlex
import util.flexbox
import ui.common.fliptoolkit.spring as animatedSpring

external interface AssignmentTableProps : RProps {
    var assignments: List<Assignment>
}

private val AssignmentTable = functionalComponent<AssignmentTableProps> { props ->

    val (expanded, setExpanded) = useState("")

    animatorBase(expanded) {
        styledTable {
            css {
                borderSpacing = 32.dp
                margin(all = (-32).dp)
                width = 100.pct
            }

            tbody {
                animated("assignmentHeader"){
                    AssignmentTableHeader()
                }
                for (assignment in props.assignments) {
                    val isExpanded = assignment.id == expanded

                    if (isExpanded) {
                        ExpandedAssignmentTopPadding()
                    }

                    animated("assignmentItem${assignment.id}") {
                        styledTr {
                            css {
                                cursor = Cursor.pointer
                            }
                            attrs.onClickFunction = {
                                if (expanded != assignment.id) {
                                    setExpanded.invoke(assignment.id)
                                } else {
                                    setExpanded.invoke("")
                                }
                            }
                            AssignmentItem(assignment)
                        }
                    }

                    if (isExpanded) {
                        animated(
                            flipID = "expandedItem",
                            flippedProps = {
                                onAppear = { element, index, decisionData ->
                                    animatedSpring(onComplete = {element.removeAttribute("style")}) { (progress, _) ->
                                        element.setAttribute("style", "opacity: ${progress}; transform-origin: 0px ${(-128).dp} 0px; transform: scaleY(${progress});")
                                    }
                                }
                            }
                        ){
                            ExpandedAssignment(assignment)
                        }
                    }
                }
            }
        }
    }
}

private fun RBuilder.ExpandedAssignmentTopPadding() {
    div { }
}

private fun RBuilder.ExpandedAssignment(assignment: Assignment) {
    styledTr {
        css {
            cursor = Cursor.pointer
            pointerEvents = PointerEvents.none
        }
        td {
            attrs {
                colSpan = "3"
            }

            styledDiv {
                css {
                    margin(top = (-120).dp, right = (-32).dp, left = (-32).dp)
                    padding(top = 120.dp, right = 32.dp, bottom = 32.dp, left = 32.dp)
                    border(2.dp, BorderStyle.solid, rgba(0, 0, 0, 0.5), 8.dp)
                }

                animated("expandedAssignmentDetails", flippedProps = {
                    scale = "false"
                    opacity = "true"
                    translate = "false"
                }){
                    div {
                        Text("CLASS SCORES", TextVarient.Bold) {
                            color = Theme.Primary
                        }

                        AssignmentStatistics(assignment.statistics)
                        DetailsItem("Feedback", "None", "message"){
                            marginTop = 32.dp
                        }
                        DetailsItem("Files", "None", "attach_file"){
                            marginTop = 24.dp
                        }
                    }
                }
            }
        }
    }
}

private fun RBuilder.AssignmentStatistics(statistics: Assignment.Statistics) {
    when (statistics) {
        Assignment.Statistics.Ungraded -> Text("Ungraded", TextVarient.Bold){
            marginTop = 16.dp
            color = rgba(0, 0, 0, 0.5)
        }
        Assignment.Statistics.Hidden -> Text("Hidden by teacher", TextVarient.Bold) {
            marginTop = 16.dp
            color = rgba(0, 0, 0, 0.5)
        }
        is Assignment.Statistics.Available -> {
            StatisticsItem("High", statistics.high)
            StatisticsItem("Low", statistics.low)
            StatisticsItem("Average", statistics.average)
            StatisticsItem("Median", statistics.median)
        }
    }
}

private fun RBuilder.StatisticsItem(type: String, grade: SubjectGrade) {
    flexbox(justifyContent = JustifyContent.spaceBetween) {
        css { marginTop = 16.dp }
        Text(type, TextVarient.Bold)
        Text(grade.toString(), TextVarient.Secondary) {
            color = rgb(0, 0, 0)
        }
    }
}

private fun RBuilder.DetailsItem(title: String, text: String, iconName: String, css: RuleSet){
    flexbox(justifyContent = JustifyContent.spaceBetween) {
        css(css)
        flexbox {
            styledDiv {
                css {
                    displayFlex(justifyContent = JustifyContent.center)
                    width = 36.dp
                    height = 36.dp
                    borderRadius = 50.pct
                    backgroundColor = Theme.Primary
                }
                MaterialIcon(iconName, size = 20.dp)
            }
            Text(title, TextVarient.Bold){
                marginLeft = 24.dp
            }
        }
        flexbox {
            Text(text, TextVarient.Secondary)
        }
    }
}

private fun RBuilder.AssignmentTableHeader() {
    tr {
        styledTh {
            css { textAlign = TextAlign.start }
            Text("ASSIGNMENT", TextVarient.Bold) {
                color = Theme.Primary
            }
        }
        styledTh {
            css { textAlign = TextAlign.end }
            Text("DUE", TextVarient.Bold) {
                color = rgba(0, 0, 0, 0.5)
            }
        }
        styledTh {
            css { textAlign = TextAlign.end }
            Text("GRADE", TextVarient.Bold) {
                color = rgba(0, 0, 0, 0.5)
            }
        }
    }
}

fun RBuilder.AssignmentTable(assignments: List<Assignment>) {
    child(AssignmentTable) {
        attrs {
            this.assignments = assignments
        }
    }
}