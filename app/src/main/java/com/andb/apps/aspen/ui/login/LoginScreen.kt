package com.andb.apps.aspen.ui.login

import androidx.compose.Composable
import androidx.compose.mutableStateOf
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.School
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.andb.apps.aspen.android.BuildConfig
import com.andb.apps.aspen.state.AppState

@Preview("LoginScreen Preview")
@Composable
fun LoginScreen() {

    val username = mutableStateOf(BuildConfig.TEST_USERNAME)
    val password = mutableStateOf(BuildConfig.TEST_PASSWORD)

    Column(
        horizontalGravity = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Stack {
            Box(
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.primary, modifier = Modifier.size(144.dp)
            )
            Icon(
                asset = Icons.Default.School.copy(defaultWidth = 72.dp, defaultHeight = 72.dp),
                tint = Color.Black.copy(alpha = 0.7f),
                modifier = Modifier.gravity(Alignment.Center)
            )
        }
        Text(
            text = "GradePoint",
            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 36.sp),
            modifier = Modifier.padding(top = 32.dp)
        )
        FilledTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Username") },
            modifier = Modifier.padding(top = 32.dp)
        )
        FilledTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Password") }
        )
        Button(
            onClick = {
                AppState.login(username.value, password.value)
            },
            modifier = Modifier.padding(top = 32.dp),
            shape = RoundedCornerShape(24.dp),
            padding = InnerPadding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)

        ) {
            Text(text = "Login".toUpperCase())
        }
    }
}