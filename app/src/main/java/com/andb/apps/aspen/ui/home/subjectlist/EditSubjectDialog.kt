package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.weight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.ui.common.ColorPicker
import com.andb.apps.aspen.ui.common.IconPicker
import com.andb.apps.aspen.util.toVectorAsset


@Composable
fun EditSubjectDialog(subject: Subject, onClose: () -> Unit, onConfigChange: (Subject.Config) -> Unit) {
    val configSaver: Saver<Subject.Config, String> = Saver(
        {value -> "${value.id},${value.color},${value.icon.name},${value.isHidden}"},
        {saved ->
            val (id, color, icon, hidden) = saved.split(",")
            Subject.Config(id, Subject.Icon.valueOf(icon), color.toInt(), hidden.toBoolean())
        }
    )
    val (currentConfig, setCurrentConfig) = savedInstanceState(saver = configSaver) { subject.config }
    AlertDialog(
        onDismissRequest = { onClose.invoke() },
        title = {
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalGravity = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Row(verticalGravity = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Stack(modifier = Modifier.padding(end = 16.dp)) {
                        Box(
                            shape = CircleShape,
                            modifier = Modifier.size(48.dp),
                            backgroundColor = Color(currentConfig.color)
                        )
                        Icon(
                            asset = currentConfig.icon.toVectorAsset(),
                            tint = Color.Black.copy(alpha = 0.54f),
                            modifier = Modifier.gravity(Alignment.Center)
                        )
                    }
                    Column {
                        Text(text = subject.name, style = MaterialTheme.typography.h6)
                        if (currentConfig.isHidden) {
                            Text(text = "Hidden", style = MaterialTheme.typography.body1)
                        }
                    }
                }
                Icon(
                    asset = if (currentConfig.isHidden) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    modifier = Modifier.clickable(onClick = {
                        setCurrentConfig(currentConfig.copy(isHidden = !currentConfig.isHidden))
                    }))
            }
        },
        text = {
            ScrollableColumn(modifier = Modifier.weight(1f)) {
                ColorPicker(selected = currentConfig.color) {
                    setCurrentConfig(currentConfig.copy(color = it))
                }
                IconPicker(selected = currentConfig.icon, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                    setCurrentConfig(currentConfig.copy(icon = it))
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
                        onConfigChange.invoke(currentConfig)
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



