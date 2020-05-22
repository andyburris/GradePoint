package com.andb.apps.aspen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.lightColorPalette
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.sp
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.assignments.AssignmentsScreen
import com.andb.apps.aspen.ui.login.LoginScreen
import com.andb.apps.aspen.ui.subjects.SubjectsScreen
import com.andb.apps.aspen.ui.subjects.SubjectsScreenLoading

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }

    override fun onBackPressed() {
        val goBack = AppState.goBack()
        Log.d("onBackPressed", "goBack = $goBack")
        if (!goBack){
            super.onBackPressed()
        }
    }
}

@Preview
@Composable
fun AppContent() {
    val currentScreen = AppState.currentScreen.collectAsState()
    AppTheme {
        Surface(color = MaterialTheme.colors.background) {
            Crossfade(current = currentScreen.value) { screen ->
                when(screen){
                    is Screen.Login -> LoginScreen()
                    is Screen.Subjects.Loading -> SubjectsScreenLoading()
                    is Screen.Subjects.List -> SubjectsScreen(subjects = screen.subjects)
                    is Screen.Assignments -> AssignmentsScreen(subject = screen.subject)
                }
            }
        }
    }
}

@Composable
fun AppTheme(content: @Composable() () -> Unit){
    MaterialTheme(
        colors = lightColorPalette(
            primary = Color(0xFF388E3C),
            primaryVariant = Color(0xFF1B5E20)
        ),
        typography = MaterialTheme.typography.copy(body1 = TextStyle(fontSize = 14.sp)),
        content = content
    )
}

