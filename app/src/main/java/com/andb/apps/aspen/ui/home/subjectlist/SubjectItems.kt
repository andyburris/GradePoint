package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextOverflow
import androidx.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.SubjectGrade
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.util.toVectorAsset

@Composable
fun SubjectItem(subject: Subject, modifier: Modifier = Modifier) {
    Box(
        modifier.clickable(onClick = { AppState.openSubject(subject) }), children = {
            Row(
                verticalGravity = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 24.dp)
            ) {
                Row(verticalGravity = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Stack(modifier = Modifier.padding(end = 16.dp)) {

                        Box(
                            shape = CircleShape,
                            modifier = Modifier.size(48.dp),
                            backgroundColor = Color(subject.config.color)
                        )
                        Icon(
                            asset = subject.config.icon.toVectorAsset(),
                            tint = Color.Black.copy(alpha = 0.54f),
                            modifier = Modifier.gravity(Alignment.Center)
                        )
                    }
                    Column {
                        Text(
                            text = subject.name,
                                style = MaterialTheme.typography.subtitle1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Text(
                                text = subject.teacher,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(end = 16.dp),
                                color = MaterialTheme.colors.onSecondary
                            )
                        }
                    }
                    val grade = subject.currentGrade
                    Text(
                        text = when {
                            grade !is SubjectGrade.Letter -> ""
                            grade.letter == null -> "${grade.number}"
                            else -> "${grade.number} ${grade.letter}"
                        },
                        style = MaterialTheme.typography.subtitle1
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

            Column(
                modifier = Modifier.padding(start = 16.dp).height(32.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(14.dp).width(120.dp),
                    backgroundColor = Color.Gray
                )
                Box(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(14.dp).width(72.dp),
                    backgroundColor = Color.Gray
                )
            }
        }
        Box(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(16.dp).width(48.dp),
            backgroundColor = Color.Gray
        )
    }
}