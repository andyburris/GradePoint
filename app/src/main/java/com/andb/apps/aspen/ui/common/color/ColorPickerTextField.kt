package com.andb.apps.aspen.ui.common.color


import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.stateFor
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.toHexString
import androidx.core.graphics.toColorInt


@Composable
fun ColorPickerTextField(selected: Color, modifier: Modifier = Modifier, onValid: (color: Color) -> Unit) {
    val currentText = stateFor(selected) { selected.toArgb().toHexString().replace("0x", "#") }

    TextField(
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