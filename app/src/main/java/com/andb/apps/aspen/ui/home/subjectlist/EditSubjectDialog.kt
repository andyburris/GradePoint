package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.AlertDialog
import androidx.ui.material.Button
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.common.ColorPicker
import com.andb.apps.aspen.ui.common.IconPicker

@Composable
fun EditSubjectDialog(subject: Subject, onClose: () -> Unit) {
    val currentConfig = state { subject.config }
    AlertDialog(
        onCloseRequest = { onClose.invoke() },
        title = { Text(text = "Edit Subject: ${subject.name}") },
        text = {
            IconPicker(selected = currentConfig.value.icon, modifier = Modifier.fillMaxWidth()) {
                currentConfig.value = currentConfig.value.copy(icon = it)
            }
            ColorPicker(selected = currentConfig.value.color) {
                currentConfig.value = currentConfig.value.copy(color = it)
            }
        },
        confirmButton = {
            Button(
                text = { Text("Done") },
                onClick = { onClose.invoke(); AppState.updateSubjectConfig(currentConfig.value) },
                backgroundColor = Color.Unset,
                elevation = 0.dp
            )
        },
        dismissButton = {
            Button(
                text = { Text("Cancel") },
                onClick = { onClose.invoke() },
                backgroundColor = Color.Unset,
                elevation = 0.dp
            )
        }
    )
}



