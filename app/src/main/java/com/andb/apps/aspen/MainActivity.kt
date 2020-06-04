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
import androidx.ui.unit.sp
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.assignment.AssignmentScreen
import com.andb.apps.aspen.ui.home.HomeScreen
import com.andb.apps.aspen.ui.home.LoadingScreen
import com.andb.apps.aspen.ui.login.LoginScreen
import com.andb.apps.aspen.ui.subject.SubjectScreen
import com.andb.apps.aspen.ui.test.TestScreen

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

@Composable
fun AppContent() {
    val currentScreen = AppState.currentScreen.collectAsState()
    AppTheme {
        Surface(color = MaterialTheme.colors.background) {
            Crossfade(current = currentScreen.value) { screen ->
                when(screen){
                    is Screen.Login -> LoginScreen()
                    is Screen.Loading -> LoadingScreen()
                    is Screen.Home -> HomeScreen(screen)
                    is Screen.Subject -> SubjectScreen(subject = screen.subject)
                    is Screen.Assignment -> AssignmentScreen(assignment = screen.assignment)
                    is Screen.Test -> TestScreen()
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

