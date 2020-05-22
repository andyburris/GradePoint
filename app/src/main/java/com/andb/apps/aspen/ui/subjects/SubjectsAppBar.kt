package com.andb.apps.aspen.ui.subjects

import androidx.compose.Composable
import androidx.ui.core.Layout
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.height
import androidx.ui.material.BottomAppBar
import androidx.ui.material.IconButton
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.AccessTime
import androidx.ui.material.icons.filled.School
import androidx.ui.material.icons.filled.Settings
import androidx.ui.unit.dp
import androidx.ui.unit.ipx

@Composable
fun SubjectsAppBar(
    fabConfig: BottomAppBar.FabConfiguration?,
    fab: @Composable() (()->Unit),
    modifier: Modifier = Modifier
){
    BottomAppBar(fabConfiguration = fabConfig, cutoutShape = RoundedCornerShape(32.dp), modifier = modifier.height(64.dp)) {
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { println("clicked grades tab") }
        ) {
            Icon(
                asset = Icons.Default.School
            )
        }
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { println("clicked recents tab") }
        ) {
            Icon(
                asset = Icons.Default.AccessTime
            )
        }
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { println("clicked settings tab") }
        ) {
            Icon(
                asset = Icons.Default.Settings
            )
        }
        Layout(children = {
            fab()
            Box(modifier = Modifier.drawBackground(Color.Blue))
        }){ measurables, constraints, _ ->
            val (fabMeasurable, boxMeasurable) = measurables.map { it.measure(constraints) }
            layout(fabMeasurable.width, fabMeasurable.height){
                boxMeasurable.place(this.parentWidth - fabMeasurable.width - 24.dp.toIntPx(), 4.ipx)
            }
        }

    }
}