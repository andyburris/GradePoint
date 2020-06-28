package com.andb.apps.aspen.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.State
import androidx.compose.collectAsState
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.toArgb
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.*
import androidx.ui.res.vectorResource
import com.andb.apps.aspen.AndroidSettings
import com.andb.apps.aspen.android.R
import com.andb.apps.aspen.model.DarkMode
import com.andb.apps.aspen.models.Category
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.collectAsState(): State<T> = (this as Flow<T>).collectAsState(value)

fun Activity.setTransparentStatusBar() {
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = Color.TRANSPARENT
    }
}

fun Category.icon() = getIconFromCategory(name)

fun getIconFromCategory(name: String) = when {
    "assessment" in name.toLowerCase() -> Icons.Default.AssignmentTurnedIn
    "participation" in name.toLowerCase() -> Icons.Default.AssignmentInd
    else -> Icons.Default.Assignment
}

@Composable
fun Subject.Icon.toVectorAsset() = when (this) {
    Subject.Icon.ART -> Icons.Default.Palette
    Subject.Icon.ATOM -> vectorResource(id = R.drawable.ic_atom)
    Subject.Icon.BOOK -> Icons.Default.Book
    Subject.Icon.CALCULUS -> vectorResource(id = R.drawable.ic_calculus)
    Subject.Icon.COMPASS -> vectorResource(id = R.drawable.ic_compass)
    Subject.Icon.COMPUTER -> Icons.Default.Laptop
    Subject.Icon.FLASK -> vectorResource(id = R.drawable.ic_flask)
    Subject.Icon.LANGUAGE -> Icons.Default.Language
    Subject.Icon.MUSIC -> Icons.Default.MusicNote
    Subject.Icon.PE -> Icons.Default.DirectionsRun
    Subject.Icon.SCHOOL -> Icons.Default.School
    Subject.Icon.BIOLOGY -> vectorResource(id = R.drawable.ic_biology)
    Subject.Icon.CAMERA -> Icons.Default.CameraAlt
    Subject.Icon.DICE -> Icons.Default.Casino
    Subject.Icon.ECONOMICS -> Icons.Default.TrendingUp
    Subject.Icon.ENGINEERING -> Icons.Default.Build
    Subject.Icon.FILM -> Icons.Default.Movie
    Subject.Icon.FINANCE -> Icons.Default.AttachMoney
    Subject.Icon.FRENCH -> Icons.Default.OutlinedFlag
    Subject.Icon.GLOBE -> Icons.Default.Public
    Subject.Icon.GOVERNMENT -> Icons.Default.AccountBalance
    Subject.Icon.HEALTH -> Icons.Default.LocalHospital
    Subject.Icon.HISTORY -> vectorResource(id = R.drawable.ic_history)
    Subject.Icon.LAW -> Icons.Default.Gavel
    Subject.Icon.NEWS -> vectorResource(id = R.drawable.ic_newspaper)
    Subject.Icon.PSYCHOLOGY -> vectorResource(id = R.drawable.ic_brain)
    Subject.Icon.SOCIOLOGY -> Icons.Default.People
    Subject.Icon.SPEAKING -> vectorResource(id = R.drawable.ic_podium)
    Subject.Icon.STATISTICS -> Icons.Default.BarChart
    Subject.Icon.THEATER -> vectorResource(id = R.drawable.ic_theater)
    Subject.Icon.TRANSLATE -> Icons.Default.Translate
    Subject.Icon.WRITING -> Icons.Default.Edit
}

fun Screen.toInboxTag() = when(this){
    Screen.Login -> "Login"
    is Screen.Home -> "Home"
    is Screen.Subject -> this.subject.id
    is Screen.Assignment -> this.assignment.id
    Screen.Test -> "Test"
}

fun MutableState<Boolean>.toggle(){
    this.value = !this.value
}

@Composable
fun DarkMode.isDark(): Boolean{
    return when(this){
        DarkMode.LIGHT -> false
        DarkMode.DARK -> true
        DarkMode.SYSTEM -> isSystemInDarkTheme()
    }
}

@Composable
fun Activity.StatusBar(currentScreen: Screen?){
    when (currentScreen) {
        is Screen.Login, is Screen.Home -> {
            window.decorView.systemUiVisibility = if (AndroidSettings.darkMode.isDark()) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = MaterialTheme.colors.background.toArgb()
        }
        is Screen.Subject, is Screen.Assignment, is Screen.Test -> {
            window.decorView.systemUiVisibility = 0
            window.statusBarColor = MaterialTheme.colors.primaryVariant.toArgb()
        }
    }
}

@Composable
fun Activity.NavigationBar(currentScreen: Screen?){
    when (currentScreen){
        is Screen.Home -> window.navigationBarColor = MaterialTheme.colors.primaryVariant.toArgb()
        else -> window.navigationBarColor = MaterialTheme.colors.primaryVariant.toArgb()
    }
}