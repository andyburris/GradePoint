package com.andb.apps.aspen.ui.common.color

import androidx.compose.Composable
import androidx.compose.stateFor
import androidx.core.graphics.toColorInt
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.graphics.toArgb
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.FilledTextField
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Colorize
import androidx.ui.unit.dp
import androidx.ui.util.toHexString

@Composable
fun ColorPickerTextField(selected: Color, modifier: Modifier = Modifier, onValid: (color: Color) -> Unit) {
    val currentText = stateFor(selected) { selected.toArgb().toHexString().replace("0x", "#") }

    FilledTextField(
        value = currentText.value.toUpperCase(),
        onValueChange = { text ->
            currentText.value = text
            println("currentText.value = ${currentText.value}")
            text.toColorIntOrNull()?.let { onValid.invoke(Color(it)) }
        },
        label = { Text(text = "Hex Color") },
        leadingIcon = { Icon(asset = Icons.Filled.Colorize) },
        modifier = modifier.padding(top = 16.dp).fillMaxWidth(),
        activeColor = selected,
        isErrorValue = currentText.value.toColorIntOrNull() == null
    )
}

private fun String.toColorIntOrNull() = try {
    this.toColorInt()
} catch (e: IllegalArgumentException) {
    null
}