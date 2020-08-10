package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumnItems
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.state
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
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
                    Orientation.Horizontal,
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