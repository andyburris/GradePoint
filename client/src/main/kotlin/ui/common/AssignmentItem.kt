package ui.common

import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Grade
import kotlinx.css.*
import react.RBuilder
import styled.css
import styled.styledDiv
import styled.styledP
import styled.styledTd
import ui.*
import util.flexbox

fun RBuilder.AssignmentItem(assignment: Assignment, showPercent: Boolean = false) {
    styledTd {
        Text(assignment.title, TextVarient.Bold) {
            margin(bottom = 4.dp)
        }
        Text(assignment.category, TextVarient.Secondary)
    }

    styledTd {
        css { textAlign = TextAlign.end }
        Text(assignment.due.format("MMM d"), TextVarient.Secondary)
    }

    styledTd {
        css { textAlign = TextAlign.end }
        flexbox {
            flexbox(direction = FlexDirection.column, alignItems = Align.flexEnd){
                css { flexGrow = 1.0 }
                Text(assignment.grade.toString(), TextVarient.Bold)
                if (showPercent && assignment.grade is Grade.Score){
                    val grade = assignment.grade as Grade.Score
                    val percent = grade.score / grade.possibleScore * 100
                    Text("$percent%", TextVarient.Bold) {
                        color = Theme.Primary
                        fontSize = 12.sp
                    }
                }
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
            width = 36.dp
            height = 36.dp
            marginLeft = 16.dp
        }
        flexbox(justifyContent = JustifyContent.center) {
            css {
                position = Position.absolute
                width = 36.dp
                height = 36.dp
            }
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