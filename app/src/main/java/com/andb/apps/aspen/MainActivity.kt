package com.andb.apps.aspen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.ProvidedValue
import androidx.compose.Providers
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
import androidx.ui.text.font.FontWeight
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
    val fontSize = AndroidSettings.fontSizeFlow.collectAsState()

    val colors = when (darkMode.value.isDark()) {
        false -> lightColorPalette(
            primary = Color(0xFF388E3C),
            primaryVariant = Color(0xFF1B5E20),
            onSecondary = Color.Black.copy(alpha = .6f)
        )
        true -> darkColorPalette(
            primary = Color(0xFF388E3C),
            primaryVariant = Color(0xFF1B5E20),
            onPrimary = Color.White,
            onSecondary = Color.White.copy(alpha = .7f)
        )
    }

    val typography = MaterialTheme.typography.copy(
        body1 = MaterialTheme.typography.body1.copy(color = colors.onSecondary, fontSize = fontSize.value.sp),
        subtitle1 = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium, fontSize = fontSize.value.sp),
        h3 = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
        h4 = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium),
        h5 = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Medium)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
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
            style = TextStyle(fontSize = 10.sp),
            color = MaterialTheme.colors.onPrimary
        )
    }
}
