package com.andb.apps.aspen.ui.home

import android.widget.Toast
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.InnerPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.AspenError
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.util.ActionHandler

@Composable
fun SubjectsErrorScreen(error: AspenError, actionHandler: ActionHandler) {
    Column {
        val context = ContextAmbient.current
        HomeHeader("Classes")
        ErrorItem(error = error, modifier = Modifier.padding(all = 24.dp)) {
            when(error){
                AspenError.OFFLINE -> actionHandler.handle(UserAction.Reload)
                else -> {
                    Toast.makeText(context, "Not possible yet - WIP", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun RecentsErrorScreen(error: AspenError, actionHandler: ActionHandler){
    Column {
        val context = ContextAmbient.current
        HomeHeader(title = "Recents")
        ErrorItem(error = error, modifier = Modifier.padding(horizontal = 24.dp)) {
            when(error){
                AspenError.OFFLINE -> actionHandler.handle(UserAction.Reload)
                else -> {
                    Toast.makeText(context, "Not possible yet - WIP", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
private fun ErrorItem(error: AspenError, modifier: Modifier = Modifier, onClickAction: () -> Unit) {
    val (icon, title, message, buttonIcon, buttonLabel) = when(error){
        AspenError.OFFLINE -> ErrorUI(Icons.Default.CloudOff, "Offline", "It seems like there’s a problem with your network. Check your data or wifi connection.", Icons.Default.Refresh, "Try Again")
        AspenError.OTHER -> ErrorUI(Icons.Default.Error, "Aspen Error", "It seems like there's a problem with Aspen. Try checking the website to see if it is functioning, and if it is then try reporting a bug for this app", Icons.Default.BugReport, "Report Bug")
    }
    Card(modifier) {
        Column(Modifier.padding(24.dp)) {
            Icon(asset = icon)
            Text(text = title, style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(top = 8.dp))
            Text(text = message, style = MaterialTheme.typography.body1, modifier = Modifier.padding(top = 8.dp))
            Button(
                onClick = onClickAction,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(top = 16.dp),
                contentPadding = InnerPadding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 12.dp)
            ) {
                Icon(asset = buttonIcon)
                Text(text = buttonLabel.toUpperCase(), modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}

private data class ErrorUI(val icon: VectorAsset, val title: String, val message: String, val buttonIcon: VectorAsset, val buttonLabel: String)