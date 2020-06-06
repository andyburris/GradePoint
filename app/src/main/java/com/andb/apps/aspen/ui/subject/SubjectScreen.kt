package com.andb.apps.aspen.ui.subject

import android.util.Log
import androidx.compose.Composable
import androidx.compose.mutableStateOf
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.toArgb
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.material.icons.filled.Clear
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.*
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.assignment.TopAppBarWithStatusBar
import com.andb.apps.aspen.ui.common.AssignmentItem
import com.andb.apps.aspen.util.icon
import com.soywiz.klock.Date

@Composable
fun SubjectScreen(subject: Subject) {
    Scaffold(
        topAppBar = {
            TopAppBarWithStatusBar(
                navigationIcon = {
                    Box(Modifier.padding(start = 12.dp).clickable(onClick = { AppState.goBack() })) {
                        Icon(
                            asset = Icons.Default.Clear
                        )
                    }
                },
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
                CategoriesCard(terms = subject.categories)
                AssignmentTable(assignments = subject.assignments)
            }
        }
    )
}

@Composable
fun CategoriesCard(terms: Map<String, List<Category>>) {
    val term = mutableStateOf(4)
    Card(
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, top = 12.dp)
    ) {
        Column(modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalGravity = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Categories".toUpperCase(),
                    style = TextStyle(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Medium
                    )
                )
                SubjectTermSelector { term.value = it }
            }

            for (category in terms[term.value.toString()]
                ?: error("Term ${term.value} Unavailable: categories are $terms")) {
                CategoryItem(category = category)
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(verticalGravity = Alignment.CenterVertically) {
            Icon(asset = category.icon(), tint = MaterialTheme.colors.onSurface.copy(alpha = .54f))
            Text(
                text = category.name,
                style = TextStyle(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(text = "(${category.weight}%)", modifier = Modifier.padding(start = 8.dp))
        }
        Text(
            text = category.average + " " + category.letter,
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
    }
}

@Composable
fun AssignmentTable(assignments: List<Assignment>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AssignmentHeader()
        for (assignment in assignments) {
            Box(Modifier.clickable(onClick = { AppState.openAssignment(assignment) })) {
                AssignmentItem(assignment = assignment, summaryText = assignment.category)
            }
        }
    }
}

@Preview
@Composable
fun AssignmentHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
    ) {
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
private fun Preview(){
    SubjectScreen(
        subject = Subject(
            "0",
            "Subject Name",
            "Teacher Name",
            Subject.Config("0", Subject.Icon.SCHOOL, MaterialTheme.colors.primary.toArgb()),
            SubjectGrade.Letter(92.35, "A-"),
            mapOf(
                Pair("1", listOf(Category("Practice and Application", "50", "92.35", "A-")))
            ),
            listOf(
                Assignment(
                    "0",
                    "Assignment Title",
                    "Category",
                    Date(2020, 4, 17),
                    Grade.Score(32.5, 36.0, "A"),
                    "Subject Name",
                    Assignment.Statistics.Hidden
                )
            )
        )
    )
}