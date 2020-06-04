package com.andb.apps.aspen.ui.common

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Done
import androidx.ui.unit.dp


@Composable
fun Chip(
    icon: VectorAsset,
    text: String,
    backgroundColor: Color = Color(0xFFE0E0E0),
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
            verticalGravity = Alignment.CenterVertically
        ) {
            Icon(asset = icon, modifier = Modifier.padding(end = 8.dp))
            Text(text = text)
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

