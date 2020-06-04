package com.andb.apps.aspen.ui.home

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Layout
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.height
import androidx.ui.material.BottomAppBar
import androidx.ui.material.BottomNavigationItem
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.AccessTime
import androidx.ui.material.icons.filled.School
import androidx.ui.material.icons.filled.Settings
import androidx.ui.unit.dp
import androidx.ui.unit.ipx
import com.andb.apps.aspen.models.HomeTab

@Composable
fun HomeAppBar(
    fabConfig: BottomAppBar.FabConfiguration?,
    fab: @Composable() (() -> Unit),
    modifier: Modifier = Modifier,
    onItemSelected: (HomeTab) -> Unit
) {
    val selected = state { HomeTab.SUBJECTS }
    BottomAppBar(
        fabConfiguration = fabConfig,
        cutoutShape = RoundedCornerShape(32.dp),
        modifier = modifier.height(64.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            icon = { Icon(asset = Icons.Default.School) },
            text = { Text(text = "Subjects") },
            selected = selected.value == HomeTab.SUBJECTS,
            onSelected = {
                selected.value = HomeTab.SUBJECTS
                onItemSelected.invoke(HomeTab.SUBJECTS)
            },
            alwaysShowLabels = false
        )
        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            icon = { Icon(asset = Icons.Default.AccessTime) },
            text = { Text(text = "Recents") },
            selected = selected.value == HomeTab.RECENTS,
            onSelected = {
                selected.value = HomeTab.RECENTS
                onItemSelected.invoke(HomeTab.RECENTS)
            },
            alwaysShowLabels = false
        )
        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            icon = { Icon(asset = Icons.Default.Settings) },
            text = { Text(text = "Settings") },
            selected = selected.value == HomeTab.SETTINGS,
            onSelected = {
                selected.value = HomeTab.SETTINGS
                onItemSelected.invoke(HomeTab.SETTINGS)
            },
            alwaysShowLabels = false
        )

        Layout(children = {
            fab()
            Box(modifier = Modifier.drawBackground(Color.Blue))
        }) { measurables, constraints, _ ->
            val (fabMeasurable, boxMeasurable) = measurables.map { it.measure(constraints) }
            layout(fabMeasurable.width, fabMeasurable.height) {
                boxMeasurable.place(this.parentWidth - fabMeasurable.width - 24.dp.toIntPx(), 4.ipx)
            }
        }

    }
}