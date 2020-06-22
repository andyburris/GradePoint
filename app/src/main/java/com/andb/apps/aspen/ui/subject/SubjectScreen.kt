package com.andb.apps.aspen.ui.subject

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.toArgb
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Clear
import androidx.ui.material.icons.filled.FilterList
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.*
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.common.AssignmentItem
import com.andb.apps.aspen.ui.common.TopAppBarWithStatusBar
import com.andb.apps.aspen.util.ActionHandler
import com.andb.apps.aspen.util.icon
import com.andb.apps.aspen.util.trimTrailingZeroes
import com.soywiz.klock.Date

@Composable
fun SubjectScreen(subject: Subject, selectedTerm: Int, actionHandler: ActionHandler) {
    Scaffold(
        topAppBar = {
            TopAppBarWithStatusBar(
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
                },
                actions = {
                    val termPickerExpanded = state { false }
                    DropdownMenu(
                        toggle = {
                            Row(
                                modifier = Modifier
                                    .clickable(onClick = { termPickerExpanded.value = true })
                                    .fillMaxHeight()
                                    .padding(horizontal = 16.dp),
                                verticalGravity = Alignment.CenterVertically
                            ) {
                                Text(text = "Term $selectedTerm".toUpperCase(), style = MaterialTheme.typography.button)
                                Icon(asset = Icons.Default.FilterList, modifier = Modifier.padding(start = 16.dp))
                            }
                        },
                        expanded = termPickerExpanded.value,
                        onDismissRequest = { termPickerExpanded.value = false })
                    {
                        subject.terms.filterIsInstance<Term.WithGrades>().forEach {
                            DropdownMenuItem(onClick = {}) {
                                Text(
                                    text = "Term ${it.term}",
                                    modifier = Modifier.clickable(onClick = {
                                        actionHandler.handle(UserAction.SwitchTerm(it.term))
                                    })
                                )
                            }
                        }
                    }
                }
            )
        },
        bodyContent = {
            if (subject.hasTerm(selectedTerm)) {
                val termGrades = subject.termGrades(selectedTerm)
                VerticalScroller {
                    CategoriesCard(categories = termGrades.categories)
                    AssignmentTable(assignments = termGrades.assignments) {
                        val screen = Screen.Assignment(it)
                        actionHandler.handle(UserAction.OpenScreen(screen))
                    }
                }
            } else {
            }
        }
    )
}

@Composable
fun CategoriesCard(categories: List<Category>) {
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
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 8.dp)
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
            style = MaterialTheme.typography.subtitle1
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
                AssignmentItem(assignment = assignment, summaryText = assignment.category)
            }
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
            Subject.Config("0", Subject.Icon.SCHOOL, MaterialTheme.colors.primary.toArgb()),
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