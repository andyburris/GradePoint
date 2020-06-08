package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Grade
import com.andb.apps.aspen.util.trimTrailingZeroes
import com.soywiz.klock.Date

@Composable
fun AssignmentItem(assignment: Assignment, summaryText: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp),
        verticalGravity = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 16.dp).weight(1f)) {
            Text(
                text = assignment.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = summaryText)
        }

/*        Text(
            text = assignment.due.format("MMM d"),
            modifier = Modifier.padding(end = 24.dp)
        )*/

        Row(
            verticalGravity = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            when (assignment.grade) {
                is Grade.Score -> (assignment.grade as Grade.Score).apply {
                    Text(
                        text = "${score.trimTrailingZeroes()}/${possibleScore.trimTrailingZeroes()}",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Stack(Modifier.offset(x = 4.dp)) {
                        CircularProgressIndicator(
                            progress = 1f,
                            modifier = Modifier.size(36.dp),
                            strokeWidth = 3.dp,
                            color = MaterialTheme.colors.primary.copy(alpha = 0.24f)
                        )
                        CircularProgressIndicator(
                            progress = (score / possibleScore).toFloat(),
                            modifier = Modifier.size(36.dp),
                            strokeWidth = 3.dp
                        )
                        Text(
                            text = letter,
                            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp),
                            modifier = Modifier.gravity(Alignment.Center)
                        )
                    }
                }
                else -> {
                    Text(
                        text = assignment.grade.toString(),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AssignmentItem(
        assignment = Assignment(
            "0",
            "Assignment Title",
            "Category",
            Date(2020, 4, 17),
            Grade.Score(32.5, 36.0, "A"),
            "Subject Name",
            Assignment.Statistics.Hidden
        ),
        summaryText = "Summary"
    )
}