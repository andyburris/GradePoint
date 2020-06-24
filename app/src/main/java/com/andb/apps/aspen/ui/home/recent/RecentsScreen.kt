package com.andb.apps.aspen.ui.home.recent

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.clickable
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.layout.padding
import androidx.ui.unit.dp
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
