package com.andb.apps.aspen.ui.home

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andb.apps.aspen.ui.common.LoadingAssignmentsItem

@Composable
fun EmptyItem(message: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(64.dp)) {
        Box(
            shape = RoundedCornerShape(topLeft = 8.dp, topRight = 8.dp),
            backgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.05f)
        ) {
            Column {
                (1..3).forEach {
                    LoadingAssignmentsItem(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp), Color.Black.copy(0.25f))
                }
                Divider(Modifier.padding(top = 16.dp))
            }
        }
        Text(text = message, style = MaterialTheme.typography.subtitle1, fontSize = 18.sp, color = MaterialTheme.colors.onSecondary, modifier = Modifier.padding(top = 16.dp))
    }
}