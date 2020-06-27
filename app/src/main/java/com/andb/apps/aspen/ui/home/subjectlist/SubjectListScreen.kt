package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.layout.Column
import androidx.ui.layout.Spacer
import androidx.ui.layout.height
import androidx.ui.material.DropdownMenu
import androidx.ui.material.DropdownMenuItem
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Edit
import androidx.ui.material.icons.filled.MoreVert
import androidx.ui.unit.Position
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.Term
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.common.SwipeStep
import com.andb.apps.aspen.ui.common.inbox.inboxItem
import com.andb.apps.aspen.ui.common.swipeable
import com.andb.apps.aspen.ui.home.HomeHeader
import com.andb.apps.aspen.util.ActionHandler

@Composable
fun SubjectsScreen(subjects: List<Subject>, term: Int, actionHandler: ActionHandler) {
    val currentEditSubject = state<Subject?> { null }

    currentEditSubject.value?.let { subject ->
        EditSubjectDialog(
            subject = subject,
            onClose = { currentEditSubject.value = null },
            onConfigChange = { actionHandler.handle(UserAction.EditConfig(it)) }
        )
    }

    Column {

        LazyColumnItems(
            items = subjects
        ) { subject ->
            val index = subjects.indexOf(subject)

            if (index == 0){
                SubjectListHeader(onLogout = { actionHandler.handle(UserAction.Logout) })
            }

            SubjectItem(
                name = subject.name,
                teacher = subject.teacher,
                config = subject.config,
                grade = subject.terms.filterIsInstance<Term.WithGrades>().find { it.term == term }!!.grade,
                modifier = Modifier.swipeable(
                    DragDirection.Horizontal,
                    DensityAmbient.current,
                    SwipeStep(
                        width = SwipeStep.Width.Size(256.dp),
                        color = MaterialTheme.colors.primary,
                        icon = Icons.Default.Edit,
                        action = {
                            currentEditSubject.value = subject
                        }
                    )
                ).clickable(
                    onClick = {
                        val screen = Screen.Subject(subject, term)
                        actionHandler.handle(UserAction.OpenScreen(screen))
                    }
                ).inboxItem(subject.id)
            )
            if (index == subjects.size - 1){
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }
}

@Composable
private fun SubjectListHeader(onLogout: ()->Unit){
    HomeHeader("Classes") {
        val expanded = state { false }
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