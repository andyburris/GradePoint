package com.andb.apps.aspen.ui.home.recent

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.clickable
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.state.AppState
import com.andb.apps.aspen.ui.common.AssignmentItem
import com.andb.apps.aspen.ui.home.subjectlist.HomeHeader

@Composable
fun RecentsScreen(recents: List<Assignment>) {
    AdapterList(data = recents) { assignment ->
        if (recents.indexOf(assignment) == 0){
            HomeHeader(title = "Recent")
        }
        Box(
            Modifier.clickable(onClick = { AppState.openAssignment(assignment) })
        ) {
            AssignmentItem(assignment = assignment, summaryText = assignment.subjectName)
        }
    }

}
