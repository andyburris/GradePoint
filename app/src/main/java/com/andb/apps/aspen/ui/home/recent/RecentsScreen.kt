package com.andb.apps.aspen.ui.home.recent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Box
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnItems
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.common.AssignmentItem
import com.andb.apps.aspen.ui.home.HomeHeader
import com.andb.apps.aspen.util.ActionHandler

@Composable
fun RecentsScreen(recents: List<Assignment>, actionHandler: ActionHandler) {
    LazyColumnItems(items = recents) { assignment ->
        if (recents.indexOf(assignment) == 0) {
            HomeHeader(title = "Recent")
        }
        Box(
            Modifier.clickable(
                onClick = {
                    val screen = Screen.Assignment(assignment)
                    actionHandler.handle(UserAction.OpenScreen(screen))
                }
            ).padding(bottom = if (recents.indexOf(assignment) == recents.size - 1) 64.dp else 0.dp)
        ) {
            AssignmentItem(assignment = assignment, summaryText = assignment.subjectName)
        }
    }
}
