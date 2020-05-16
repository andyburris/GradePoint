package com.andb.apps.aspen.ui.login

import androidx.compose.Composable
import androidx.compose.mutableStateOf
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.School
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.andb.apps.aspen.AndroidAppState
import com.andb.apps.aspen.models.Screen

@Preview("LoginScreen Preview")
@Composable
fun LoginScreen(){

    val username = mutableStateOf("")
    val password = mutableStateOf("")

    Column(horizontalGravity = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Box(gravity = ContentGravity.Center) {
            Box(shape = CircleShape, backgroundColor = MaterialTheme.colors.primary, modifier = Modifier.size(144.dp))
            Icon(asset = Icons.Default.School.copy(defaultWidth = 72.dp, defaultHeight = 72.dp), tint = Color.Black.copy(alpha = 0.7f))
        }
        FilledTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Username") },
            modifier = Modifier.padding(top = 16.dp)
        )
        FilledTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Password") }
        )
        Button(
            onClick = {
                AndroidAppState.screen = Screen.Subjects()
                AndroidAppState.login(username.value, password.value)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Login")
        }
    }
}