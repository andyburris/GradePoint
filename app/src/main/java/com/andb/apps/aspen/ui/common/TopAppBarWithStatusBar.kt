package com.andb.apps.aspen.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Box
import androidx.compose.foundation.drawBackground
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBarWithStatusBar(
    title: @Composable() () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable() (() -> Unit)? = null,
    actions: @Composable() RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primary,
    statusColor: Color = MaterialTheme.colors.primaryVariant,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 4.dp
) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(24.dp).drawBackground(color = statusColor)
        )
        TopAppBar(
            title,
            modifier,
            navigationIcon,
            actions,
            backgroundColor,
            contentColor,
            elevation
        )
    }
}