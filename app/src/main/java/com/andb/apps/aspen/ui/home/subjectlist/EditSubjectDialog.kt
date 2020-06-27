package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.layout.ColumnScope.weight
import androidx.ui.material.AlertDialog
import androidx.ui.material.Button
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.unit.dp
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
            VerticalScroller(modifier = Modifier.weight(1f)) {
                ColorPicker(selected = currentConfig.value.color) {
                    currentConfig.value = currentConfig.value.copy(color = it)
                }
                IconPicker(selected = currentConfig.value.icon, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                    currentConfig.value = currentConfig.value.copy(icon = it)
                }
            }
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp).offset(x = 8.dp), horizontalArrangement = Arrangement.End) {
                Button(
                    text = { Text("Cancel".toUpperCase()) },
                    onClick = { onClose.invoke() },
                    backgroundColor = Color.Unset,
                    elevation = 0.dp
                )
                Button(
                    text = { Text("Done".toUpperCase()) },
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



