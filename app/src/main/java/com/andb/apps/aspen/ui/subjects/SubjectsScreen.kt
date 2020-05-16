package com.andb.apps.aspen.ui.subjects

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.*
import androidx.ui.material.ripple.ripple
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.AndroidAppState

@Composable
fun SubjectsScreen(subjects: List<Subject>) {
    Column {
        Text(
            text = "Classes",
            style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        )
        AdapterList(data = subjects, modifier = Modifier.padding(start = 16.dp, end = 16.dp)) { subject ->
            SubjectItem(subject = subject)
        }
    }
}

@Composable
fun SubjectItem(subject: Subject) {
    Clickable(
        onClick = { AndroidAppState.screen = Screen.Assignments(subject) },
        modifier = Modifier.ripple(color = MaterialTheme.colors.primary),
        children = {
            Row(
                verticalGravity = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)
            ) {
                Row(verticalGravity = Alignment.CenterVertically) {
                    Box(gravity = ContentGravity.Center, modifier = Modifier.padding(end = 16.dp)) {
                        val icon: VectorAsset = when (subject.icon) {
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
                        Box(shape = CircleShape, modifier = Modifier.size(48.dp), backgroundColor = Color(subject.color))
                        Icon(asset = icon)
                    }
                    Column {
                        Text(text = subject.name, style = TextStyle(fontWeight = FontWeight.Bold))
                        Text(text = subject.teacher)
                    }
                }
                Text(
                    text = "${subject.currentGrade} ${subject.currentGradeLetter}",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
            }
        }
    )
}