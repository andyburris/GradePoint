package com.andb.apps.aspen.ui.assignments

import android.util.Log
import androidx.compose.Composable
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
import androidx.ui.unit.Dp
import androidx.ui.unit.dp
import com.andb.apps.aspen.data.PlaceholderData
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Subject

@Composable
fun AssignmentsScreen(subject: Subject){
    Scaffold(
        topAppBar = {
            TopAppBarWithStatusBar(
                title = { Text(text = subject.name) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { Log.d("jetpackCompose", "FAB clicked") },
                icon = { Icon(asset = Icons.Default.Add) }
            )
        },
        bodyContent = {
            Box(paddingStart = 16.dp, paddingEnd = 16.dp) {
                AssignmentTable(assignments = PlaceholderData.assignments)
            }
        }
    )
}

@Composable
fun AssignmentTable(assignments: List<Assignment>) {
    Table(
        columns = 3,
        columnWidth = { index ->
            when (index) {
                0 -> TableColumnWidth.Flex(1f)
                else -> TableColumnWidth.MaxIntrinsic
            }
        },
        children = {
            tableRow {
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
            for (assignment in assignments) {
                tableRow {
                    AspenItem(assignment)
                }
            }
        }
    )
}

@Composable
fun AspenItem(assignment: Assignment) {
    Column (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp).fillMaxSize()){
        Text(
            text = assignment.title,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Text(text = assignment.category)
    }

    Text(
        text = assignment.due,
        modifier = Modifier.padding(end = 24.dp).height(36.dp)
    )

    Row(
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "${assignment.score.stripTrailingZeroesString()}/${assignment.possibleScore.stripTrailingZeroesString()}",
            style = TextStyle(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(end = 12.dp)
        )
        Box(gravity = ContentGravity.Center) {
            CircularProgressIndicator(
                progress = (assignment.score/assignment.possibleScore).toFloat(),
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = "${assignment.scoreLetter}",
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
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
){
    Column(modifier = Modifier.drawBackground(color = statusBarColor)) {
        Surface(modifier = Modifier.fillMaxWidth().height(24.dp)){}
        TopAppBar(title, modifier, navigationIcon, actions, backgroundColor, contentColor, elevation)
    }
}

private fun Double.stripTrailingZeroesString(): String = this.toBigDecimal().stripTrailingZeros().toPlainString()