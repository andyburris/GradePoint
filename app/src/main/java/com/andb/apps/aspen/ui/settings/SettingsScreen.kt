package com.andb.apps.aspen.ui.settings

import android.os.Build
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.AlertDialog
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.RadioGroup
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.FormatSize
import androidx.ui.material.icons.filled.Settings
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import com.andb.apps.aspen.AndroidSettings
import com.andb.apps.aspen.android.BuildConfig
import com.andb.apps.aspen.android.R
import com.andb.apps.aspen.model.DarkMode
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.common.Chip
import com.andb.apps.aspen.ui.home.HomeHeader

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
        val darkModeDialogShown = state { false }
        SettingsItem(
            title = "Dark Mode",
            icon = vectorResource(id = R.drawable.ic_weather_night),
            modifier = Modifier.clickable(onClick = { darkModeDialogShown.value = true })
                .padding(horizontal = 24.dp, vertical = 16.dp)
        )
        DarkModeDialog(
            showing = darkModeDialogShown.value,
            onClose = { darkModeDialogShown.value = false }
        )

        SettingsItem(
            title = "Font Size",
            icon = Icons.Default.FormatSize,
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        ) {
            Chip(
                text = "14 (default)",
                selected = AndroidSettings.fontSize == 14,
                onClick = { AndroidSettings.fontSize = 14 },
                modifier = Modifier.padding(end = 4.dp)
            )
            Chip(
                text = "16",
                selected = AndroidSettings.fontSize == 16,
                onClick = { AndroidSettings.fontSize = 16 })
        }
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
                Text(text = title, style = MaterialTheme.typography.subtitle1)
                if (summary != null) Text(text = summary)
            }
        }
        Row(children = widget)
    }
}

@Composable
fun DarkModeDialog(showing: Boolean, onClose: () -> Unit) {
    val selectedMode = state { AndroidSettings.darkMode }
    if (showing) {
        AlertDialog(
            onCloseRequest = onClose,
            title = { Text(text = "Dark Mode") },
            text = {
                RadioGroup {
                    RadioGroupTextItem(
                        selected = selectedMode.value == DarkMode.LIGHT,
                        onSelect = { selectedMode.value = DarkMode.LIGHT },
                        radioColor = MaterialTheme.colors.primary,
                        text = "Light"
                    )
                    RadioGroupTextItem(
                        selected = selectedMode.value == DarkMode.DARK,
                        onSelect = { selectedMode.value = DarkMode.DARK },
                        radioColor = MaterialTheme.colors.primary,
                        text = "Dark"
                    )
                    RadioGroupTextItem(
                        selected = selectedMode.value == DarkMode.SYSTEM,
                        onSelect = { selectedMode.value = DarkMode.SYSTEM },
                        radioColor = MaterialTheme.colors.primary,
                        text = if (Build.VERSION.SDK_INT >= 29) "Follow System" else "Follow Battery Saver"
                    )
                }
            },
            dismissButton = {
                Button(onClick = onClose, backgroundColor = Color.Unset, elevation = 0.dp) {
                    Text(text = "Cancel".toUpperCase())
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        AndroidSettings.darkMode = selectedMode.value
                        onClose.invoke()
                    },
                    backgroundColor = Color.Unset,
                    elevation = 0.dp
                ) {
                    Text(text = "Done".toUpperCase())
                }
            }
        )
    }
}

