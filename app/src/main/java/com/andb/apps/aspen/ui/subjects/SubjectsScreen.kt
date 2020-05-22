package com.andb.apps.aspen.ui.subjects

import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.Providers
import androidx.compose.mutableStateOf
import androidx.ui.animation.DpPropKey
import androidx.ui.animation.Transition
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.ExtendedFloatingActionButton
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.*
import androidx.ui.material.ripple.ripple
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.andb.apps.aspen.models.Grade
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.common.shimmer.DefaultLinearShimmerEffectFactory
import com.andb.apps.aspen.ui.common.shimmer.Direction.LeftToRight
import com.andb.apps.aspen.ui.common.shimmer.ShimmerTheme
import com.andb.apps.aspen.ui.common.shimmer.ShimmerThemeAmbient
import com.andb.apps.aspen.ui.common.shimmer.shimmer

@Composable
fun SubjectsScreen(subjects: List<Subject>) {
    val expanded = mutableStateOf(false)
    val fabPaddingKey = DpPropKey()
    val appBarHeightKey = DpPropKey()
    val definition = transitionDefinition<Boolean> {
        state(false) {
            this[fabPaddingKey] = 0.dp
            this[appBarHeightKey] = 64.dp
        }
        state(true) {
            this[fabPaddingKey] = 100.dp
            this[appBarHeightKey] = 0.dp
        }

        transition {
            fabPaddingKey using tween { duration = 200 }
        }
    }

    val fab: @Composable() ()->Unit = {
        ExtendedFloatingActionButton(
            icon = {
                Icon(asset = Icons.Default.FilterList)
            },
            text = {
                Transition(definition = definition, toState = expanded.value) {
                    Text(text = "Term".toUpperCase(), modifier = Modifier.padding(end = it[fabPaddingKey]))
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            onClick = {
                expanded.value = !expanded.value
                println("Term clicked")
            }
        )
    }
    Scaffold(
        floatingActionButton = fab,
        floatingActionButtonPosition = Scaffold.FabPosition.EndDocked,
        bottomAppBar = { fabConfig ->
            Transition(definition = definition, toState = expanded.value) {
                SubjectsAppBar(fabConfig = fabConfig, fab = fab)
            }
        },
        bodyContent = {
            Column {
                SubjectsHeader()
                AdapterList(
                    data = subjects
                ) { subject ->
                    SubjectItem(subject = subject)
                }
            }
        }
    ) 

}

@Composable
fun SubjectsScreenLoading() {
    Column {
        SubjectsHeader()
        Providers(
            ShimmerThemeAmbient provides ShimmerTheme(
                factory = DefaultLinearShimmerEffectFactory,
                baseAlpha = 0.2f,
                highlightAlpha = 0.6f,
                direction = LeftToRight,
                dropOff = 0.5f,
                intensity = 0f,
                tilt = 20f
            )
        ){
            AdapterList(data = List(8) { null }) {
                LoadingSubjectsItem(modifier = Modifier.shimmer())
            }
        }
    }
}

@Composable
fun SubjectsHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 48.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
    ) {
        Text(
            text = "Classes",
            style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Bold)
        )
        IconButton(onClick = {}) {
            Icon(asset = Icons.Default.MoreVert)
        }
    }
}

@Composable
fun SubjectItem(subject: Subject) {
    Clickable(
        onClick = { AppState.openSubject(subject) },
        modifier = Modifier.ripple(color = MaterialTheme.colors.primary),
        children = {
            Row(
                verticalGravity = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 24.dp)
            ) {
                Row(verticalGravity = Alignment.CenterVertically) {
                    Stack(modifier = Modifier.padding(end = 16.dp)) {
                        val icon: VectorAsset = when (subject.config.icon) {
                            Subject.Icon.ART -> Icons.Default.Palette
                            Subject.Icon.ATOM -> Icons.Default.Clear
                            Subject.Icon.BOOK -> Icons.Default.Book
                            Subject.Icon.CALCULUS -> Icons.Default.Clear
                            Subject.Icon.COMPASS -> Icons.Default.Clear
                            Subject.Icon.COMPUTER -> Icons.Default.Laptop
                            Subject.Icon.FLASK -> Icons.Default.Clear
                            Subject.Icon.LANGUAGE -> Icons.Default.Language
                            Subject.Icon.MUSIC -> Icons.Default.MusicNote
                            Subject.Icon.PE -> Icons.Default.DirectionsRun
                            Subject.Icon.SCHOOL -> Icons.Default.School
                        }
                        Box(
                            shape = CircleShape,
                            modifier = Modifier.size(48.dp),
                            backgroundColor = Color(subject.config.color)
                        )
                        Icon(
                            asset = icon,
                            tint = Color.Black.copy(alpha = 0.7f),
                            modifier = Modifier.gravity(Alignment.Center)
                        )
                    }
                    Column {
                        Text(text = subject.name, style = TextStyle(fontWeight = FontWeight.Bold))
                        Text(text = subject.teacher)
                    }
                }
                val grade = subject.currentGrade
                Text(
                    text = when {
                        grade !is Grade.Letter -> ""
                        grade.letter == null -> "${grade.number}"
                        else -> "${grade.number} ${grade.letter}"
                    },
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
            }
        }
    )
}

@Composable
fun LoadingSubjectsItem(modifier: Modifier = Modifier) {
    Row(
        verticalGravity = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 24.dp) + modifier
    ) {
        Row(verticalGravity = Alignment.CenterVertically) {
            Box(
                shape = CircleShape,
                modifier = Modifier.size(48.dp),
                backgroundColor = Color.Gray
            )

            Column(modifier = Modifier.padding(start = 16.dp).height(32.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Box(shape = RoundedCornerShape(4.dp), modifier = Modifier.height(14.dp).width(120.dp), backgroundColor = Color.Gray)
                Box(shape = RoundedCornerShape(4.dp), modifier = Modifier.height(14.dp).width(72.dp), backgroundColor = Color.Gray)
            }
        }
        Box(shape = RoundedCornerShape(4.dp), modifier = Modifier.height(16.dp).width(48.dp), backgroundColor = Color.Gray)
    }
}