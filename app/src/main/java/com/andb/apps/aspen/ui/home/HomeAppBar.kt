package com.andb.apps.aspen.ui.home

import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Layout
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.HomeTab


@Composable
fun HomeAppBar(
    selectedTab: HomeTab,
    fab: @Composable() (() -> Unit),
    modifier: Modifier = Modifier,
    onItemSelected: (HomeTab) -> Unit
) {
    BottomAppBar(
        cutoutShape = if (selectedTab == HomeTab.SUBJECTS) RoundedCornerShape(32.dp) else null,
        modifier = modifier.height(64.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            icon = { Icon(asset = Icons.Default.School) },
            label = { Text(text = "Subjects", color = MaterialTheme.colors.onPrimary, maxLines = 1) },
            selected = selectedTab == HomeTab.SUBJECTS,
            onSelect = { onItemSelected.invoke(HomeTab.SUBJECTS) },
            alwaysShowLabels = false
        )
        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            icon = { Icon(asset = Icons.Default.AccessTime) },
            label = { Text(text = "Recents", color = MaterialTheme.colors.onPrimary, maxLines = 1) },
            selected = selectedTab == HomeTab.RECENTS,
            onSelect = { onItemSelected.invoke(HomeTab.RECENTS) },
            alwaysShowLabels = false
        )
        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            icon = { Icon(asset = Icons.Default.Settings) },
            label = { Text(text = "Settings", color = MaterialTheme.colors.onPrimary, maxLines = 1) },
            selected = selectedTab == HomeTab.SETTINGS,
            onSelect = { onItemSelected.invoke(HomeTab.SETTINGS) },
            alwaysShowLabels = false
        )

        Layout(children = {
            fab()
            Box(modifier = Modifier.background(Color.Blue))
        }) { measurables, constraints ->
            val (fabMeasurable, boxMeasurable) = measurables.map { it.measure(constraints) }
            layout(fabMeasurable.width, fabMeasurable.height) {
                boxMeasurable.place(Offset.Zero)
            }
        }

    }
}