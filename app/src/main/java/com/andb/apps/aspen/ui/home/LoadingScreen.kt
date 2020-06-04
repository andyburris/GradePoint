package com.andb.apps.aspen.ui.home

import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.layout.Column
import com.andb.apps.aspen.ui.common.shimmer.*
import com.andb.apps.aspen.ui.home.subjectlist.HomeHeader
import com.andb.apps.aspen.ui.home.subjectlist.LoadingSubjectsItem

@Composable
fun LoadingScreen() {
    Column {
        HomeHeader("Classes")
        Providers(
            ShimmerThemeAmbient provides ShimmerTheme(
                factory = DefaultLinearShimmerEffectFactory,
                baseAlpha = 0.2f,
                highlightAlpha = 0.6f,
                direction = Direction.LeftToRight,
                dropOff = 0.5f,
                intensity = 0f,
                tilt = 20f
            )
        ) {
            AdapterList(data = List(8) { null }) {
                LoadingSubjectsItem(
                    modifier = Modifier.shimmer()
                )
            }
        }
    }
}