package com.andb.apps.aspen.ui.settings

import android.os.Build
import androidx.compose.Composable
import androidx.compose.collectAsState
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
import androidx.ui.material.icons.filled.Palette
import androidx.ui.material.icons.filled.Settings
import androidx.ui.material.icons.outlined.Info
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import com.andb.apps.aspen.AndroidSettings
import com.andb.apps.aspen.android.BuildConfig
import com.andb.apps.aspen.android.R
import com.andb.apps.aspen.model.DarkMode
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.common.Chip
import com.andb.apps.aspen.ui.home.HomeHeader
import com.andb.apps.aspen.util.ActionHandler

@Composable
fun SettingsScreen(actionHandler: ActionHandler) {
    Column {
        HomeHeader(title = "Settings")
        Spacer(modifier = Modifier.height(8.dp))
        SettingsHeaderItem(title = "Theme", topPadding = false)
        val darkModeDialogShown = state { false }
        SettingsItem(
            title = "Dark Mode",
            icon = vectorResource(id = R.drawable.ic_weather_night),
            modifier = Modifier.clickable(onClick = { darkModeDialogShown.value = true })
        )
        DarkModeDialog(
            showing = darkModeDialogShown.value,
            onClose = { darkModeDialogShown.value = false }
        )

        SettingsHeaderItem(title = "Experiments")
        SettingsItem(
            title = "Font Size",
            icon = Icons.Default.FormatSize
        ) {
            Chip(
                text = "14sp (default)",
                selected = AndroidSettings.fontSize == 14,
                onClick = { AndroidSettings.fontSize = 14 },
                modifier = Modifier.padding(end = 4.dp)
            )
            Chip(
                text = "16sp",
                selected = AndroidSettings.fontSize == 16,
                onClick = { AndroidSettings.fontSize = 16 })
        }

        val color = AndroidSettings.assignmentHeaderColorFlow.collectAsState()
        SettingsItem(
            title = "Assignment Page Header Color",
            icon = Icons.Default.Palette
        ) {
            Chip(
                text = "Background",
                selected = !color.value,
                onClick = { AndroidSettings.assignmentHeaderColor = false },
                modifier = Modifier.padding(end = 4.dp)
            )
            Chip(
                text = "Green",
                selected = color.value,
                onClick = { AndroidSettings.assignmentHeaderColor = true })
        }

        if (BuildConfig.DEBUG) {
            SettingsItem(
                title = "Test",
                summary = "Open test screen",
                icon = Icons.Default.Settings,
                modifier = Modifier.clickable(onClick = {
                    actionHandler.handle(UserAction.OpenScreen(Screen.Test))
                })
            )
        }

        SettingsHeaderItem(title = "About")
        SettingsItem(
            title = "Version",
            icon = Icons.Outlined.Info,
            summary = "v" + BuildConfig.VERSION_NAME
        )
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
        modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(verticalGravity = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
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
fun SettingsHeaderItem(title: String, topPadding: Boolean = true) {
    Text(
        text = title,
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(start = 64.dp, top = if (topPadding) 24.dp else 0.dp, bottom = 8.dp)
    )
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

