package com.andb.apps.aspen.ui.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.dp


@Composable
fun Chip(
    icon: VectorAsset? = null,
    text: String? = null,
    backgroundColor: Color = MaterialTheme.colors.onBackground.copy(alpha = .12f),
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.height(32.dp).clickable(onClick = onClick),
        backgroundColor = backgroundColor
    ) {
        Row(
            modifier = Modifier.fillMaxHeight().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null){
                Icon(asset = icon.copy(defaultWidth = 20.dp, defaultHeight = 20.dp), modifier = Modifier.padding(end = 8.dp))
            }
            if (text != null){
                Text(text = text, style = MaterialTheme.typography.body2, color = contentColorFor(color = backgroundColor))
            }
            if (selected) {
                Box(
                    shape = CircleShape,
                    modifier = Modifier.padding(start = 8.dp).size(24.dp),
                    backgroundColor = Color.Gray,
                    gravity = ContentGravity.Center
                ) {
                    Icon(asset = Icons.Default.Done, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

