package com.andb.apps.aspen.ui.assignment

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.LinearProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.BorderClear
import androidx.ui.material.icons.filled.Clear
import androidx.ui.material.icons.filled.Event
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Grade
import com.andb.apps.aspen.models.SubjectGrade
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.util.ActionHandler
import com.andb.apps.aspen.util.getIconFromCategory
import com.andb.apps.aspen.util.toDecimalString
import com.andb.apps.aspen.util.trimTrailingZeroes
import com.soywiz.klock.Date

@Composable
fun AssignmentScreen(assignment: Assignment, actionHandler: ActionHandler) {
    MaterialTheme(typography = MaterialTheme.typography.copy(
        subtitle1 = MaterialTheme.typography.subtitle1.copy(fontSize = 16.sp),
        body1 = MaterialTheme.typography.body1.copy(fontSize = 16.sp)
    )) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Row(Modifier.padding(top = 48.dp), verticalGravity = Alignment.CenterVertically) {
                Icon(
                    asset = Icons.Default.Clear,
                    modifier = Modifier.clickable(onClick = { actionHandler.handle(UserAction.Back) }),
                    tint = contentColor().copy(alpha = .54f)
                )
                Text(
                    text = assignment.subjectName,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.padding(start = 24.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = assignment.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 24.dp)
            )
            Stack(modifier = Modifier.padding(top = 48.dp)) {
                when (assignment.grade) {
                    is Grade.Score -> ExtendedScoreItem(score = (assignment.grade as Grade.Score))
                    else -> EmptyGradeItem(grade = assignment.grade)
                }
            }
            DetailItem(
                title = "Category",
                text = assignment.category,
                icon = getIconFromCategory(assignment.category),
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
            )

            DetailItem(
                title = "Due",
                text = assignment.due.format("MMM d"),
                icon = Icons.Default.Event,
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
            )

            Text(
                text = "Class Scores".toUpperCase(),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(top = 48.dp)
            )

            when (assignment.statistics) {
                is Assignment.Statistics.Hidden -> Text(
                    text = "Hidden by teacher",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(top = 24.dp)
                )
                is Assignment.Statistics.Ungraded -> Text(
                    text = "Ungraded",
                    style = MaterialTheme.typography.subtitle1,
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
}

@Composable
private fun ExtendedScoreItem(score: Grade.Score, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalGravity = Alignment.Bottom) {
            Row {
                Text(
                    text = "${score.score.trimTrailingZeroes()}/${score.possibleScore.trimTrailingZeroes()}",
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = score.letter,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = "${(score.score / score.possibleScore * 100).toDecimalString(2).trimTrailingZeroes()}%",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary
            )
        }
        Row(Modifier.fillMaxWidth().padding(top = 8.dp), verticalGravity = Alignment.CenterVertically) {
            LinearProgressIndicator(
                progress = (score.score / score.possibleScore).toFloat(),
                modifier = Modifier.weight(1f)
            )

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
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.gravity(Alignment.Center)
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "${grade.score}/${grade.possibleScore}",
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "${(grade.score / grade.possibleScore * 100).toDecimalString(2)
                    .trimTrailingZeroes()}%",
                style = MaterialTheme.typography.body1
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
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun DetailItem(title: String, text: String, icon: VectorAsset, modifier: Modifier) {
    Row(verticalGravity = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
        Row(verticalGravity = Alignment.CenterVertically) {
            Stack(
                modifier = Modifier.size(32.dp).drawBackground(MaterialTheme.colors.primary, shape = CircleShape)
            ) {
                Icon(
                    asset = icon.copy(defaultHeight = 20.dp, defaultWidth = 20.dp),
                    tint = Color.Black.copy(alpha = .54f),
                    modifier = Modifier.gravity(Alignment.Center)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Composable
private fun StatisticItem(type: String, value: SubjectGrade, modifier: Modifier = Modifier) {
    Row(
        verticalGravity = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = type, style = MaterialTheme.typography.subtitle1)
        when (value) {
            is SubjectGrade.Letter -> Text(
                text = "${value.number} ${value.letter}",
                style = MaterialTheme.typography.body1
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
        ),
        actionHandler = ActionHandler { true }
    )
}