package com.andb.apps.aspen.ui.home

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.dp
import androidx.ui.unit.sp

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
            style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Bold)
        )
        Row(verticalGravity = Alignment.CenterVertically, children = actions)
    }
}
