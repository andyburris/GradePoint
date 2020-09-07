package com.andb.apps.aspen.ui.subject

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.andb.apps.aspen.models.*
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.common.AssignmentItem
import com.andb.apps.aspen.ui.home.EmptyItem
import com.andb.apps.aspen.ui.common.FabState
import com.andb.apps.aspen.ui.common.TermSwitcherFAB
import com.andb.apps.aspen.util.ActionHandler
import com.andb.apps.aspen.util.trimTrailingZeroes
import com.soywiz.klock.Date
import com.andb.apps.aspen.util.*

@Composable
fun SubjectScreen(subject: Subject, selectedTerm: Int, actionHandler: ActionHandler) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    Box(
                        Modifier.padding(start = 12.dp).clickable(onClick = { actionHandler.handle(UserAction.Back) })
                    ) {
                        Icon(
                            asset = Icons.Default.Clear
                        )
                    }
                },
                title = {
                    Text(
                        text = subject.name,
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        },
        floatingActionButton = {
            val expanded = savedInstanceState { false }
            TermSwitcherFAB(
                fabState = if (expanded.value) FabState.EXPANDED else FabState.COLLAPSED,
                currentTerm = selectedTerm,
                possibleTerms = subject.terms.filter { subject.hasTerm(it.term) }.map { it.term },
                onFabExpandedChanged = { expanded.value = it},
                onTermChanged = { actionHandler.handle(UserAction.SwitchTerm(it))}
            )
        },
        bodyContent = {
            if (subject.hasTerm(selectedTerm)) {
                val termGrades = subject.termGrades(selectedTerm)
                ScrollableColumn {
                    CategoriesCard(categories = termGrades.categories, termGrade = termGrades.grade)
                    AssignmentTable(assignments = termGrades.assignments) {
                        val screen = Screen.Assignment(it)
                        actionHandler.handle(UserAction.OpenScreen(screen))
                    }
                }
            }
        }
    )
}

@Composable
fun CategoriesCard(categories: List<Category>, termGrade: SubjectGrade) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 4.dp)
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
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primary
                )
            }

            for (category in categories) {
                CategoryItem(category = category)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalGravity = Alignment.CenterVertically
            ) {
                Text(text = "Total", style = MaterialTheme.typography.subtitle1, color = MaterialTheme.colors.onSecondary)
                Text(text = termGrade.toString(), style = MaterialTheme.typography.subtitle1)
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
        Row(
            verticalGravity = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(asset = category.icon(), tint = MaterialTheme.colors.onSurface.copy(alpha = .54f))
            Text(
                text = category.name,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp).weight(1f, fill = false)
            )
            Text(
                text = "(${category.weight.trimTrailingZeroes()}%)",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(
            text = category.average + " " + category.letter,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun AssignmentTable(assignments: List<Assignment>, onAssignmentClick: (Assignment) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AssignmentHeader()
        for (assignment in assignments) {
            Box(Modifier.clickable(
                onClick = {
                    onAssignmentClick.invoke(assignment)
                })
            ) {
                AssignmentItem(assignment = assignment, summaryText = assignment.category, modifier = Modifier/*.inboxItem(assignment.id)*/)
            }
        }
        if (assignments.isEmpty()){
            EmptyItem(message = "No Grades Yet")
        }
    }
}

@Composable
fun AssignmentHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 12.dp)
    ) {
        Text(
            text = "Assignment".toUpperCase(),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary
        )
        Text(
            text = "Grade".toUpperCase(),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SubjectScreen(
        subject = Subject(
            "0",
            "Subject Name",
            "Teacher Name",
            Subject.Config("0", Subject.Icon.SCHOOL, MaterialTheme.colors.primary.toArgb(), false),
            listOf(
                Term.WithGrades(
                    4,
                    grade = SubjectGrade.Letter(92.35, "A-"),
                    assignments = listOf(
                        Assignment(
                            "0",
                            "Assignment Title",
                            "Category",
                            Date(2020, 4, 17),
                            Grade.Score(32.5, 36.0, "A"),
                            "Subject Name",
                            Assignment.Statistics.Hidden
                        )
                    ),
                    categories = listOf(Category("Practice and Application", "50", "92.35", "A-"))
                )
            )
        ),
        selectedTerm = 4,
        actionHandler = ActionHandler { true }
    )
}