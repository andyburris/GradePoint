package com.andb.apps.aspen.ui.common

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.util.*


@Composable
fun IconPicker(selected: Subject.Icon, modifier: Modifier = Modifier, onSelect: (Subject.Icon) -> Unit) {
    Column(modifier) {
        Text(
            text = "Icon",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Flexbox {
            for (icon in Subject.Icon.values()) {
                Chip(
                    icon = icon.toVectorAsset(),
                    text = icon.name.toLowerCase().capitalize(),
                    //backgroundColor = MaterialTheme.colors.background,
                    selected = icon == selected,
                    modifier = Modifier.padding(bottom = 4.dp, end = 4.dp),
                    onClick = { onSelect.invoke(icon) }
                )
                Box()
            }
        }
    }
}