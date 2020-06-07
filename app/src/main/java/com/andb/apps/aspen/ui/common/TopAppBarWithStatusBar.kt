package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.drawBackground
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.RowScope
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.material.contentColorFor
import androidx.ui.unit.Dp
import androidx.ui.unit.dp

@Composable
fun TopAppBarWithStatusBar(
    title: @Composable() () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable() (() -> Unit)? = null,
    actions: @Composable() RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 4.dp
) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(24.dp).drawBackground(color = backgroundColor)
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