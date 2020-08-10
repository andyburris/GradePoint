package com.andb.apps.aspen.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.dp

@Composable
fun HomeHeader(title: String, actions: @Composable() RowScope.() -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h3
        )
        Row(verticalGravity = Alignment.CenterVertically, children = actions)
    }
}
