package com.andb.apps.aspen.ui.home

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import androidx.ui.unit.sp

@Composable
fun HomeTermSwitcher(modifier: Modifier = Modifier) {
    val currentTerm = state { 4 }
    Stack(modifier = modifier) {
        Box(
            shape = CircleShape,
            backgroundColor = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(start = 52.dp * (currentTerm.value - 1)).size(36.dp).gravity(Alignment.CenterStart)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.gravity(Alignment.Center)
        ) {
            val numberStyle = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center, fontSize = 16.sp)
            Text(text = "1",
                style = numberStyle,
                modifier = Modifier.padding(end = 16.dp).width(36.dp).clickable(onClick = { currentTerm.value = 1 })
            )
            Text(
                text = "2",
                style = numberStyle,
                modifier = Modifier.padding(end = 16.dp).width(36.dp).clickable(onClick = { currentTerm.value = 2 })
            )
            Text(
                text = "3",
                style = numberStyle,
                modifier = Modifier.padding(end = 16.dp).width(36.dp).clickable(onClick = { currentTerm.value = 3 })
            )
            Text(
                text = "4",
                style = numberStyle,
                modifier = Modifier.width(36.dp).clickable(onClick = { currentTerm.value = 4 })
            )
        }
    }
}