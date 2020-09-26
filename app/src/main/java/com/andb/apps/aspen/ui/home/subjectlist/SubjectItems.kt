package com.andb.apps.aspen.ui.home.subjectlist

import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.SubjectGrade
import com.andb.apps.aspen.util.*


@Composable
fun SubjectItem(config: Subject.Config, name: String, teacher: String, grade: SubjectGrade, modifier: Modifier = Modifier) {
    Box(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Stack(modifier = Modifier.padding(end = 16.dp)) {

                    Box(
                        shape = CircleShape,
                        modifier = Modifier.size(48.dp),
                        backgroundColor = Color(config.color)
                    )
                    Icon(
                        asset = config.icon.toVectorAsset(),
                        tint = Color.Black.copy(alpha = 0.54f),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        text = teacher,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 16.dp),
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            }
            Text(
                text = grade.toString(),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
fun LoadingSubjectsItem(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 24.dp) + modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                shape = CircleShape,
                modifier = Modifier.size(48.dp),
                backgroundColor = MaterialTheme.colors.onSecondary
            )

            Column(
                modifier = Modifier.padding(start = 16.dp).height(32.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    shape = CircleShape,
                    modifier = Modifier.height(14.dp).width(120.dp),
                    backgroundColor = MaterialTheme.colors.onSecondary
                )
                Box(
                    shape = CircleShape,
                    modifier = Modifier.height(14.dp).width(72.dp),
                    backgroundColor = MaterialTheme.colors.onSecondary
                )
            }
        }
        Box(
            shape = CircleShape,
            modifier = Modifier.height(16.dp).width(48.dp),
            backgroundColor = MaterialTheme.colors.onSecondary
        )
    }
}