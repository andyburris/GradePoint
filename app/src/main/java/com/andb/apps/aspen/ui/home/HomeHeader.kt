package com.andb.apps.aspen.ui.home

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp

@Composable
fun HomeHeader(title: String, actions: @Composable() RowScope.() -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 48.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h3
        )
        Row(verticalGravity = Alignment.CenterVertically, children = actions)
    }
}
