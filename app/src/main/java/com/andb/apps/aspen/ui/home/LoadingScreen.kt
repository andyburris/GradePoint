package com.andb.apps.aspen.ui.home

import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.Modifier
import androidx.ui.layout.Column
import com.andb.apps.aspen.ui.common.LoadingAssignmentsItem
import com.andb.apps.aspen.ui.common.shimmer.*
import com.andb.apps.aspen.ui.home.subjectlist.LoadingSubjectsItem

private val shimmer = ShimmerTheme(
    factory = DefaultLinearShimmerEffectFactory,
    baseAlpha = 0.2f,
    highlightAlpha = 0.6f,
    direction = Direction.LeftToRight,
    dropOff = 0.5f,
    intensity = 0f,
    tilt = 20f
)

@Composable
fun SubjectsLoadingScreen() {
    Column {
        HomeHeader("Classes")
        Providers(ShimmerThemeAmbient provides shimmer) {
            repeat(8){
                LoadingSubjectsItem(
                    modifier = Modifier.shimmer()
                )
            }
        }
    }
}

@Composable
fun RecentsLoadingScreen(){
    Column {
        HomeHeader(title = "Recent")
        Providers(ShimmerThemeAmbient provides shimmer) {
            repeat(12){
                LoadingAssignmentsItem(modifier = Modifier.shimmer())
            }
        }
    }
}