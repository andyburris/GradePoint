package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.util.toVectorAsset

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