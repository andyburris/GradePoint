package com.andb.apps.aspen.ui.settings

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Settings
import androidx.ui.res.vectorResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.dp
import com.andb.apps.aspen.android.BuildConfig
import com.andb.apps.aspen.android.R
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.home.subjectlist.HomeHeader

@Composable
fun SettingsScreen() {
    Column {
        HomeHeader(title = "Settings")
        if (BuildConfig.DEBUG) {
            SettingsItem(
                title = "Test",
                summary = "Open test screen",
                icon = Icons.Default.Settings,
                modifier = Modifier.clickable(onClick = { AppState.openTest() })
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            )
        }
        SettingsItem(title = "Dark Mode",
            icon = vectorResource(id = R.drawable.ic_weather_night),
            modifier = Modifier.clickable(onClick = {})
                .padding(horizontal = 24.dp, vertical = 16.dp))
    }
}

@Composable
fun SettingsItem(
    title: String,
    summary: String? = null,
    icon: VectorAsset,
    modifier: Modifier = Modifier,
    widget: @Composable() RowScope.() -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalGravity = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(verticalGravity = Alignment.CenterVertically) {
            Icon(asset = icon)
            Column(Modifier.padding(start = 16.dp)) {
                Text(text = title, style = TextStyle(fontWeight = FontWeight.Medium))
                if (summary != null) Text(text = summary)
            }
        }
        Row(children = widget)
    }
}