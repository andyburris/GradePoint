package com.andb.apps.aspen.ui.assignments

import android.util.Log
import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.Dp
import androidx.ui.unit.dp
import com.andb.apps.aspen.data.PlaceholderData
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Grade
import com.andb.apps.aspen.models.Subject

@Composable
fun AssignmentsScreen(subject: Subject) {
    Scaffold(
        topAppBar = {
            TopAppBarWithStatusBar(
                title = { Text(text = subject.name) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { Log.d("jetpackCompose", "FAB clicked") },
                icon = { Icon(asset = Icons.Default.Add) },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        bodyContent = {
            VerticalScroller {
                AssignmentTable(assignments = subject.assignments)
            }
        }
    )
}

@Composable
fun AssignmentTable(assignments: List<Assignment>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AssignmentHeader()
        for (assignment in assignments) {
            AssignmentItem(assignment = assignment)
        }
    }
}

@Preview
@Composable
fun AssignmentHeader() {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
        Text(
            text = "Assignment".toUpperCase(),
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
            style = TextStyle(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = "Due".toUpperCase(),
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
        Text(
            text = "Grade".toUpperCase(),
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
    }
}

@Preview
@Composable
fun AssignmentItem(assignment: Assignment) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 16.dp),
        verticalGravity = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 16.dp).weight(1f)) {
            Text(
                text = assignment.title,
                style = TextStyle(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = assignment.category)
        }

        Text(
            text = assignment.due.format("MMM d"),
            modifier = Modifier.padding(end = 24.dp)
        )

        Row(
            verticalGravity = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            when(assignment.grade){
                is Grade.Score -> (assignment.grade as Grade.Score).apply {
                    Text(
                        text = "${score.stripTrailingZeroesString()}/${possibleScore.stripTrailingZeroesString()}",
                        style = TextStyle(fontWeight = FontWeight.Medium),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Stack {
                        CircularProgressIndicator(
                            progress = (score / possibleScore).toFloat(),
                            modifier = Modifier.size(36.dp),
                            strokeWidth = 3.dp
                        )
                        Text(
                            text = assignment.scoreLetter,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.gravity(Alignment.Center)
                        )
                    }
                }
                is Grade.Empty -> {
                    Text(
                        text = (assignment.grade as Grade.Empty).message,
                        style = TextStyle(fontWeight = FontWeight.Medium)
                    )
                }
                is Grade.Missing -> {
                    Text(
                        text = "Missing/${(assignment.grade as Grade.Missing).possibleScore}",
                        style = TextStyle(fontWeight = FontWeight.Medium)
                    )
                }
                Grade.Ungraded -> {
                    Text(
                        text = "Ungraded",
                        style = TextStyle(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }
    }
}

@Composable
fun TopAppBarWithStatusBar(
    title: @Composable() () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable() (() -> Unit)? = null,
    actions: @Composable() RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primaryVariant,
    statusBarColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 4.dp
) {
    Column(modifier = Modifier.drawBackground(color = statusBarColor)) {
        Surface(modifier = Modifier.fillMaxWidth().height(24.dp)) {}
        TopAppBar(
            title,
            modifier,
            navigationIcon,
            actions,
            backgroundColor,
            contentColor,
            elevation
        )
    }
}

private fun Double.stripTrailingZeroesString(): String =
    this.toBigDecimal().stripTrailingZeros().toPlainString()