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
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.*
import androidx.ui.res.vectorResource
import com.andb.apps.aspen.android.R
import com.andb.apps.aspen.model.DarkMode
import com.andb.apps.aspen.models.Category
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