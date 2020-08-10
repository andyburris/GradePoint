package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.foundation.Text
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.weight
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.ui.common.ColorPicker
import com.andb.apps.aspen.ui.common.IconPicker


@Composable
fun EditSubjectDialog(subject: Subject, onClose: () -> Unit, onConfigChange: (Subject.Config) -> Unit) {
    val currentConfig = savedInstanceState { subject.config }
    AlertDialog(
        onCloseRequest = { onClose.invoke() },
        title = { Text(text = "Edit Subject: ${subject.name}") },
        text = {
            ScrollableColumn(modifier = Modifier.weight(1f)) {
                ColorPicker(selected = currentConfig.value.color) {
                    currentConfig.value = currentConfig.value.copy(color = it)
                }
                IconPicker(selected = currentConfig.value.icon, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                    currentConfig.value = currentConfig.value.copy(icon = it)
                }
            }
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp).offset(x = 8.dp), horizontalArrangement = Arrangement.End) {
                Button(
                    content = { Text("Cancel".toUpperCase()) },
                    onClick = { onClose.invoke() },
                    backgroundColor = Color.Unset,
                    elevation = 0.dp
                )
                Button(
                    content = { Text("Done".toUpperCase()) },
                    onClick = {
                        onConfigChange.invoke(currentConfig.value)
                        onClose.invoke()
                    },
                    backgroundColor = Color.Unset,
                    elevation = 0.dp
                )
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}



