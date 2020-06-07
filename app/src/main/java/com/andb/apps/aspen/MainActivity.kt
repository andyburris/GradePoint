package com.andb.apps.aspen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.ui.animation.Crossfade
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.drawLayer
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.size
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.andb.apps.aspen.android.BuildConfig
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.assignment.AssignmentScreen
import com.andb.apps.aspen.ui.home.HomeScreen
import com.andb.apps.aspen.ui.home.LoadingScreen
import com.andb.apps.aspen.ui.login.LoginScreen
import com.andb.apps.aspen.ui.subject.SubjectScreen
import com.andb.apps.aspen.ui.test.TestScreen
import com.andb.apps.aspen.util.isDark

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
        if (!goBack) {
            super.onBackPressed()
        }
    }
}

@Composable
fun AppContent() {
    val currentScreen = AppState.currentScreen.collectAsState()
    AppTheme {
        Surface(color = MaterialTheme.colors.background) {
            Stack {
                Crossfade(current = currentScreen.value) { screen ->
                    when (screen) {
                        is Screen.Login -> LoginScreen()
                        is Screen.Loading -> LoadingScreen()
                        is Screen.Home -> HomeScreen(screen)
                        is Screen.Subject -> SubjectScreen(subject = screen.subject)
                        is Screen.Assignment -> AssignmentScreen(assignment = screen.assignment)
                        is Screen.Test -> TestScreen()
                    }
                }
                if (BuildConfig.DEBUG) {
                    VersionRibbon(Modifier.gravity(Alignment.TopEnd))
                }
            }
        }
    }
}

@Composable
fun AppTheme(content: @Composable() () -> Unit) {
    val darkMode = AndroidSettings.darkModeFlow.collectAsState()
    MaterialTheme(
        colors = when (darkMode.value.isDark()) {
            false -> lightColorPalette(
                primary = Color(0xFF388E3C),
                primaryVariant = Color(0xFF1B5E20)
            )
            true -> darkColorPalette(
                primary = Color(0xFF388E3C),
                primaryVariant = Color(0xFF1B5E20),
                onPrimary = Color.White
            )
        },
        typography = MaterialTheme.typography.copy(body1 = TextStyle(fontSize = 14.sp)),
        content = content
    )
}


@Composable
fun VersionRibbon(modifier: Modifier = Modifier) {
    Stack(
        modifier = modifier.size(108.dp)
            .drawLayer(rotationZ = 45f, translationX = 75f, translationY = -75f)
    ) {
        Box(
            modifier = Modifier.height(24.dp).fillMaxWidth().gravity(Alignment.Center),
            backgroundColor = MaterialTheme.colors.primary
        )
        Text(
            text = BuildConfig.VERSION_NAME,
            modifier = Modifier.gravity(Alignment.Center),
            style = TextStyle(color = MaterialTheme.colors.onPrimary, fontSize = 10.sp)
        )
    }
}
