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
import androidx.ui.material.icons.filled.Settings
import androidx.ui.material.icons.filled.UnfoldMore
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
        if (BuildConfig.DEBUG) {
            SettingsItem(
                title = "Test",
                summary = "Open test screen",
                icon = Icons.Default.Settings,
                modifier = Modifier
                    .clickable(onClick = {
                        actionHandler.handle(UserAction.OpenScreen(Screen.Test))
                    })
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)
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

        val spacing = AndroidSettings.assignmentSpacingFlow.collectAsState()
        SettingsItem(
            title = "Assignment List Spacing",
            icon = Icons.Default.UnfoldMore,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Chip(
                text = "8dp (default)",
                selected = spacing.value == 8,
                onClick = { AndroidSettings.assignmentSpacing = 8 },
                modifier = Modifier.padding(end = 4.dp)
            )
            Chip(
                text = "12dp",
                selected = spacing.value == 12,
                onClick = { AndroidSettings.assignmentSpacing = 12 })
        }

        SettingsItem(
            title = "About",
            icon = Icons.Outlined.Info,
            summary = "v" + BuildConfig.VERSION_NAME,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)
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
        modifier = modifier.fillMaxWidth()
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

