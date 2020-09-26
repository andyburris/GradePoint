package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyColumnItems
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.Position
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.Term
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.common.SwipeStep
import com.andb.apps.aspen.ui.common.inbox.inboxItem
import com.andb.apps.aspen.ui.common.swipeable
import com.andb.apps.aspen.ui.home.HomeHeader
import com.andb.apps.aspen.util.ActionHandler
import com.andb.apps.aspen.util.SettingsAmbient
import com.andb.apps.aspen.util.collectAsState


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SubjectsScreen(subjects: List<Subject>, term: Int, actionHandler: ActionHandler) {
    val currentEditSubject = savedInstanceState<String?> { null }
    val hiddenExpanded = savedInstanceState { false }

    val (visible, hidden) = subjects.partition { !it.config.isHidden }

    currentEditSubject.value?.let { subjectID ->
        EditSubjectDialog(
            subject = subjects.first { it.id == subjectID },
            onClose = { currentEditSubject.value = null },
            onConfigChange = { actionHandler.handle(UserAction.EditConfig(it)) }
        )
    }

    ScrollableColumn {
        SubjectListHeader(onLogout = { actionHandler.handle(UserAction.Logout) })

        LazyColumnFor(
            items = visible,
            itemContent = { subject ->
                SubjectItem(
                    name = subject.name,
                    teacher = subject.teacher,
                    config = subject.config,
                    grade = subject.terms.filterIsInstance<Term.WithGrades>().find { it.term == term }!!.grade,
                    modifier = Modifier.clickable(
                        onClick = {
                            val screen = Screen.Subject(subject, term)
                            actionHandler.handle(UserAction.OpenScreen(screen))
                        },
                        onLongClick = { currentEditSubject.value = subject.id }
                    )
                )
            }
        )

        val showHidden = SettingsAmbient.current.showHiddenFlow.collectAsState()
        AnimatedVisibility(visible = hidden.isNotEmpty() && showHidden.value) {
            Column {
                HiddenHeader(hiddenSubjectCount = hidden.size, expanded = hiddenExpanded.value, onToggle = { hiddenExpanded.value = !hiddenExpanded.value })
                AnimatedVisibility(visible = hiddenExpanded.value){
                    LazyColumnFor(
                        items = hidden,
                        itemContent = { subject ->
                            SubjectItem(
                                name = subject.name,
                                teacher = subject.teacher,
                                config = subject.config,
                                grade = subject.terms.filterIsInstance<Term.WithGrades>().find { it.term == term }!!.grade,
                                modifier = Modifier.clickable(
                                    onClick = {
                                        val screen = Screen.Subject(subject, term)
                                        actionHandler.handle(UserAction.OpenScreen(screen))
                                    },
                                    onLongClick = { currentEditSubject.value = subject.id }
                                )
                            )
                        },
                        modifier = Modifier.animateContentSize()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(72.dp))
    }
}

@Composable
private fun SubjectListHeader(onLogout: ()->Unit){
    HomeHeader("Classes") {
        val expanded = remember { mutableStateOf(false) }
        DropdownMenu(
            toggle = {
                IconButton(onClick = { expanded.value = !expanded.value }) {
                    Icon(asset = Icons.Default.MoreVert)
                }
            },
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            dropdownOffset = Position(x = (-24).dp, y = 0.dp)
        ) {
            DropdownMenuItem(enabled = true, onClick = onLogout) {
                Text(text = "Log Out")
            }
        }
    }
}

@Composable
private fun HiddenHeader(hiddenSubjectCount: Int, expanded: Boolean, onToggle: () -> Unit){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onToggle).padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        Text(
            text = "$hiddenSubjectCount Hidden Class" + if (hiddenSubjectCount != 1) "es" else "",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSecondary
        )

        val rotation = animate(target = if (expanded) 0f else -90f)
        Icon(
            asset = Icons.Default.KeyboardArrowDown,
            modifier = Modifier.drawLayer(rotationZ = rotation),
            tint = MaterialTheme.colors.onSecondary
        )
    }
}