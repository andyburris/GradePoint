package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.layout.Column
import androidx.ui.material.DropdownMenu
import androidx.ui.material.DropdownMenuItem
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Edit
import androidx.ui.material.icons.filled.MoreVert
import androidx.ui.unit.Position
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.common.SwipeStep
import com.andb.apps.aspen.ui.common.swipeable

@Composable
fun SubjectsScreen(subjects: List<Subject>) {
    val currentEditSubject = state<Subject?> { null }

    currentEditSubject.value?.let {
        EditSubjectDialog(subject = it, onClose = { currentEditSubject.value = null })
    }

    Column {
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
                DropdownMenuItem(enabled = true, onClick = { AppState.logout() }) {
                    Text(
                        text = "Log Out",
                        modifier = Modifier.clickable(onClick = { AppState.logout() })
                    )
                }
            }
        }
        AdapterList(
            data = subjects
        ) { subject ->
            SubjectItem(
                subject = subject,
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
                )
            )
        }
    }
}

