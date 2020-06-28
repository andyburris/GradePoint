package com.andb.apps.aspen.ui.assignment

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.LinearProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Clear
import androidx.ui.material.icons.filled.Event
import androidx.ui.material.icons.filled.NotInterested
import androidx.ui.text.style.TextAlign
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
    AssignmentTextSize {
        Column(modifier = Modifier.drawBackground(MaterialTheme.colors.background).fillMaxSize()) {
            Header(
                assignment = assignment,
                onCloseClick = { actionHandler.handle(UserAction.Back) }
            )
            VerticalScroller(modifier = Modifier.padding(horizontal = 24.dp)) {
                Stack(modifier = Modifier.padding(top = 24.dp)) {
                    when (assignment.grade) {
                        is Grade.Score, is Grade.Missing -> ExtendedScoreItem(grade = assignment.grade)
                        else -> DetailItem(
                            title = assignment.grade.toString(),
                            text = "",
                            icon = Icons.Default.NotInterested
                        )
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

                AssignmentStatistics(statistics = assignment.statistics)

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun AssignmentTextSize(children: @Composable() () -> Unit){
    MaterialTheme(
        typography = MaterialTheme.typography.copy(
            subtitle1 = MaterialTheme.typography.subtitle1.copy(fontSize = 16.sp),
            body1 = MaterialTheme.typography.body1.copy(fontSize = 16.sp)
        ),
        content = children
    )
}

@Composable
private fun Header(assignment: Assignment, modifier: Modifier = Modifier, onCloseClick: ()->Unit){
    Surface(elevation = 4.dp, color = MaterialTheme.colors.primary) {
        Column(modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            Row(Modifier.padding(top = 24.dp), verticalGravity = Alignment.CenterVertically) {
                Icon(
                    asset = Icons.Default.Clear,
                    modifier = Modifier.clickable(onClick = onCloseClick),
                    tint = MaterialTheme.colors.onPrimary.copy(alpha = .54f)
                )
                Text(
                    text = assignment.subjectName,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary.copy(alpha = .7f),
                    modifier = Modifier.padding(start = 24.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = assignment.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
private fun ExtendedScoreItem(grade: Grade, modifier: Modifier = Modifier) {
    val score = when(grade){
        is Grade.Score -> grade.score.trimTrailingZeroes()
        is Grade.Missing -> "Missing"
        else -> throw Error("Extended score item should only have Grade.Score or Grade.Missing")
    }
    val possibleScore = when(grade){
        is Grade.Score -> grade.possibleScore.trimTrailingZeroes()
        is Grade.Missing -> grade.possibleScore.trimTrailingZeroes()
        else -> throw Error()
    }
    val letter = when(grade){
        is Grade.Score -> grade.letter
        is Grade.Missing -> "F"
        else -> throw Error()
    }
    val percent = when(grade){
        is Grade.Score -> grade.score/grade.possibleScore
        is Grade.Missing -> 0.0
        else -> throw Error()
    }

    Column(modifier = modifier) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalGravity = Alignment.Bottom) {
            Row {
                Text(
                    text = "$score/$possibleScore",
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = letter,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = "${(percent * 100).toDecimalString(2).trimTrailingZeroes()}%",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary
            )
        }
        LinearProgressIndicator(
            progress = percent.toFloat(),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
    }
}

@Composable
private fun DetailItem(title: String, text: String, icon: VectorAsset, modifier: Modifier = Modifier) {
    Row(verticalGravity = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
        Row(verticalGravity = Alignment.CenterVertically) {
            Stack(
                modifier = Modifier.size(32.dp).drawBackground(MaterialTheme.colors.primary, shape = CircleShape)
            ) {
                Icon(
                    asset = icon.copy(defaultHeight = 16.dp, defaultWidth = 16.dp),
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
        Text(text = text, style = MaterialTheme.typography.body1, textAlign = TextAlign.End)
    }
}

@Composable
private fun AssignmentStatistics(statistics: Assignment.Statistics){
    Text(
        text = "Class Scores".toUpperCase(),
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(top = 32.dp)
    )

    when (statistics) {
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
            StatisticItem(
                type = "High",
                value = statistics.high,
                modifier = Modifier.padding(top = 24.dp)
            )
            StatisticItem(
                type = "Low",
                value = statistics.low,
                modifier = Modifier.padding(top = 16.dp)
            )
            StatisticItem(
                type = "Average",
                value = statistics.average,
                modifier = Modifier.padding(top = 16.dp)
            )
            StatisticItem(
                type = "Median",
                value = statistics.median,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
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
            else -> { }
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