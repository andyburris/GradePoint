package com.andb.apps.aspen.ui.common

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Grade
import com.andb.apps.aspen.ui.common.shimmer.shimmer
import com.andb.apps.aspen.util.trimTrailingZeroes
import com.soywiz.klock.Date


@Composable
fun AssignmentItem(assignment: Assignment, summaryText: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(horizontal = 24.dp, vertical = 12.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(end = 16.dp).weight(1f)) {
            Text(
                text = assignment.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = summaryText, modifier = Modifier.padding(top = 4.dp))
        }

/*        Text(
            text = assignment.due.format("MMM d"),
            modifier = Modifier.padding(end = 24.dp)
        )*/

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            when (assignment.grade) {
                is Grade.Score -> (assignment.grade as Grade.Score).apply {
                    Text(
                        text = "${score.trimTrailingZeroes()}/${possibleScore.trimTrailingZeroes()}",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Stack {
                        CircularProgressIndicator(
                            progress = 1f,
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 3.dp,
                            color = MaterialTheme.colors.primary.copy(alpha = 0.24f)
                        )
                        CircularProgressIndicator(
                            progress = (score / possibleScore).coerceAtMost(1.0).toFloat(),
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 3.dp
                        )
                        Text(
                            text = letter,
                            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
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

@Composable
fun LoadingAssignmentsItem(modifier: Modifier = Modifier, color: Color = MaterialTheme.colors.onSecondary) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Box(Modifier.size(width = 120.dp, height = 14.dp), backgroundColor = color, shape = CircleShape)
            Box(Modifier.padding(top = 4.dp).size(width = 72.dp, height = 14.dp), backgroundColor = color, shape = CircleShape)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.padding(end = 8.dp).size(width = 32.dp, height = 14.dp), backgroundColor = color, shape = CircleShape)
            Box(Modifier.size(32.dp), backgroundColor = color, shape = CircleShape)
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