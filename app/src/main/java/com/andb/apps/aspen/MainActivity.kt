package com.andb.apps.aspen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import com.andb.apps.aspen.data.PlaceholderData
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.ui.assignments.AssignmentsScreen
import com.andb.apps.aspen.ui.login.LoginScreen
import com.andb.apps.aspen.ui.subjects.SubjectsScreen

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
        when(AndroidAppState.screen){
            is Screen.Assignments -> AndroidAppState.screen = Screen.Subjects()
            is Screen.Subjects -> AndroidAppState.screen = Screen.Login
            else -> super.onBackPressed()
        }
    }
}

@Preview
@Composable
fun AppContent() {
    Surface(color = MaterialTheme.colors.background) {
        Crossfade(current = AndroidAppState.screen) { screen ->
            when(screen){
                is Screen.Login -> LoginScreen()
                is Screen.Subjects -> SubjectsScreen(subjects = PlaceholderData.subjects)
                is Screen.Assignments -> AssignmentsScreen(subject = screen.subject)
            }
        }
    }
}

