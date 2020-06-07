package com.andb.apps.aspen.ui.assignment

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.contentColor
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.BorderClear
import androidx.ui.material.icons.filled.Clear
import androidx.ui.material.icons.filled.Event
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Grade
import com.andb.apps.aspen.models.SubjectGrade
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.util.getIconFromCategory
import com.andb.apps.aspen.util.toDecimalString
import com.andb.apps.aspen.util.trimTrailingZeroes
import com.soywiz.klock.Date

@Composable
fun AssignmentScreen(assignment: Assignment) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
        Row(Modifier.padding(top = 48.dp), verticalGravity = Alignment.CenterVertically) {
            Icon(
                asset = Icons.Default.Clear,
                modifier = Modifier.clickable(onClick = { AppState.goBack() }),
                tint = contentColor().copy(alpha = .54f)
            )
            Text(
                text = assignment.subjectName,
                style = TextStyle(
                    color = MaterialTheme.colors.onBackground.copy(alpha = .54f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(start = 24.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = assignment.title,
            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 36.sp),
            modifier = Modifier.padding(top = 24.dp)
        )
        Stack(modifier = Modifier.padding(top = 32.dp)) {
            when (assignment.grade) {
                is Grade.Score -> ScoreItem(grade = (assignment.grade as Grade.Score))
                else -> EmptyGradeItem(grade = assignment.grade)
            }
        }
        DetailItem(
            text = assignment.category,
            icon = getIconFromCategory(assignment.category),
            modifier = Modifier.padding(top = 24.dp)
        )

        DetailItem(
            text = assignment.due.format("MMM d"),
            icon = Icons.Default.Event,
            modifier = Modifier.padding(top = 24.dp)
        )

        Text(
            text = "Class Scores".toUpperCase(),
            style = TextStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(top = 32.dp)
        )

        when (assignment.statistics) {
            is Assignment.Statistics.Hidden -> Text(
                text = "Hidden by teacher",
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp),
                modifier = Modifier.padding(top = 24.dp)
            )
            is Assignment.Statistics.Ungraded -> Text(
                text = "Ungraded",
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp),
                modifier = Modifier.padding(top = 24.dp)
            )
            is Assignment.Statistics.Available -> {
                val stats = assignment.statistics as Assignment.Statistics.Available
                StatisticItem(
                    type = "High",
                    value = stats.high,
                    modifier = Modifier.padding(top = 24.dp)
                )
                StatisticItem(
                    type = "Low",
                    value = stats.low,
                    modifier = Modifier.padding(top = 16.dp)
                )
                StatisticItem(
                    type = "Average",
                    value = stats.average,
                    modifier = Modifier.padding(top = 16.dp)
                )
                StatisticItem(
                    type = "Median",
                    value = stats.median,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun ScoreItem(grade: Grade.Score, modifier: Modifier = Modifier) {
    Row(verticalGravity = Alignment.CenterVertically, modifier = modifier) {
        Stack {
            CircularProgressIndicator(progress = (grade.score / grade.possibleScore).toFloat())
            Text(
                text = grade.letter,
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp),
                modifier = Modifier.gravity(Alignment.Center)
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "${grade.score}/${grade.possibleScore}",
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp)
            )
            Text(
                text = "${(grade.score / grade.possibleScore * 100).toDecimalString(2)
                    .trimTrailingZeroes()}%",
                style = TextStyle(fontSize = 18.sp)
            )
        }
    }
}

@Composable
private fun EmptyGradeItem(grade: Grade, modifier: Modifier = Modifier) {
    Row(verticalGravity = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            asset = Icons.Default.BorderClear.copy(defaultWidth = 36.dp, defaultHeight = 36.dp),
            tint = MaterialTheme.colors.onSurface.copy(alpha = .54f)
        )
        Text(
            text = grade.toString(),
            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun DetailItem(text: String, icon: VectorAsset, modifier: Modifier) {
    Row(verticalGravity = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            asset = icon.copy(defaultWidth = 36.dp, defaultHeight = 36.dp),
            tint = MaterialTheme.colors.onSurface.copy(alpha = .54f)
        )
        Text(
            text = text,
            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun StatisticItem(type: String, value: SubjectGrade, modifier: Modifier = Modifier) {
    Row(
        verticalGravity = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = type, style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp))
        when (value) {
            is SubjectGrade.Letter -> Text(
                text = "${value.number} ${value.letter}",
                style = TextStyle(fontSize = 18.sp)
            )
            else -> {
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AssignmentScreen(
        assignment = Assignment(
            "0",
            "Assignment Title",
            "Category",
            Date(2020, 4, 17),
            Grade.Score(32.5, 36.0, "A"),
            "Subject Name",
            Assignment.Statistics.Available(
                low = SubjectGrade.Letter(0.0, "F"),
                high = SubjectGrade.Letter(36.0, "A"),
                average = SubjectGrade.Letter(24.3, "D+"),
                median = SubjectGrade.Letter(26.0, "C-")
            )
        )
    )
}