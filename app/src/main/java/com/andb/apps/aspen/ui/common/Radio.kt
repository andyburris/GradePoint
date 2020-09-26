package com.andb.apps.aspen.ui.common

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.currentTextStyle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun RadioItem(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    radioColor: Color = MaterialTheme.colors.secondary,
    textStyle: TextStyle? = null,
    onSelect: (Boolean) -> Unit,
    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onSelect.invoke(!selected) }).padding(vertical = 8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = { onSelect.invoke(!selected) },
            selectedColor = radioColor
        )
        Text(
            text = text,
            style = textStyle ?: currentTextStyle(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}