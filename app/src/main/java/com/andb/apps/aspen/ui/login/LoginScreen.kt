package com.andb.apps.aspen.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.andb.apps.aspen.android.BuildConfig
import com.andb.apps.aspen.android.R
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.util.ActionHandler


@Composable
fun LoginScreen(actionHandler: ActionHandler) {

    val username = mutableStateOf(BuildConfig.TEST_USERNAME)
    val password = mutableStateOf(BuildConfig.TEST_PASSWORD)
    val titleText = mutableStateOf("GradePoint")

    Column(
        horizontalGravity = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(shape = CircleShape, elevation = 4.dp) {
            Image(
                asset = vectorResource(id = R.drawable.ic_fullbleed_gradepoint_icon),
                modifier = Modifier.clip(CircleShape).size(144.dp)
            )
        }
        Text(
            text = titleText.value,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(top = 32.dp)
        )
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Username") },
            modifier = Modifier.padding(top = 32.dp)
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it; println("password = ${password.value}") },
            label = { Text(text = "Password") },
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            onClick = {
                titleText.value = "Clicked (${password.value.length})"
                val sanitizedUsername = username.value.filter { it >= ' ' }
                val sanitizedPassword = password.value.filter { it >= ' ' }
                actionHandler.handle(UserAction.Login(sanitizedUsername, sanitizedPassword))
            },
            modifier = Modifier.padding(top = 32.dp),
            shape = RoundedCornerShape(32.dp),
            padding = InnerPadding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)

        ) {
            Text(text = "Log in".toUpperCase(), color = MaterialTheme.colors.onPrimary)
        }
    }
}

@Preview
@Composable
private fun PreviewLogin(){
    LoginScreen(actionHandler = ActionHandler { true })
}