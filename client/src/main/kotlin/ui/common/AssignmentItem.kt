package ui.common

import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Grade
import kotlinx.css.*
import kotlinx.html.TR
import react.RBuilder
import react.dom.RDOMBuilder
import styled.css
import styled.styledDiv
import styled.styledP
import styled.styledTd
import ui.Text
import ui.TextVarient
import ui.Theme
import ui.sp
import util.flexbox

fun RDOMBuilder<TR>.AssignmentItem(assignment: Assignment) {
    styledTd {
        Text(assignment.title, TextVarient.Bold) {
            margin(bottom = 4.px)
        }
        Text(assignment.category, TextVarient.Secondary)
    }

    styledTd {
        css { textAlign = TextAlign.end }
        Text(assignment.due.format("MMM d"), TextVarient.Secondary)
    }

    styledTd {
        css { textAlign = TextAlign.end }
        flexbox(alignItems = Align.center) {
            Text(assignment.grade.toString(), TextVarient.Bold) {
                flexGrow = 1.0
            }
            if (assignment.grade is Grade.Score) {
                GradeCircle(assignment.grade as Grade.Score)
            }
        }
    }
}

private fun RBuilder.GradeCircle(grade: Grade.Score) {
    styledDiv {
        css {
            width = 36.px
            height = 36.px
            marginLeft = 16.px
        }
        flexbox(justifyContent = JustifyContent.center, alignItems = Align.center, otherCSS = {
            position = Position.absolute
            width = 36.px
            height = 36.px
        }) {
            styledP {
                css {
                    fontSize = 16.sp
                    fontWeight = FontWeight.w600
                }
                +grade.letter
            }
        }
        Circle {
            attrs {
                strokeColor = Theme.Primary.value
                strokeWidth = ((3.0/36) * 100)
                percent = (grade.score/grade.possibleScore) * 100
            }
        }

    }
}