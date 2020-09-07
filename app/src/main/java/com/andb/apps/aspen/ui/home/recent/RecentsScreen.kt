package com.andb.apps.aspen.ui.home.recent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Box
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyColumnItems
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.state.UserAction
import com.andb.apps.aspen.ui.common.AssignmentItem
import com.andb.apps.aspen.ui.home.EmptyItem
import com.andb.apps.aspen.ui.home.HomeHeader
import com.andb.apps.aspen.util.ActionHandler

@Composable
fun RecentsScreen(recents: List<Assignment>, actionHandler: ActionHandler) {
    if (recents.isEmpty()){
        Column(Modifier.fillMaxHeight()) {
            HomeHeader(title = "Recent")
            EmptyItem(message = "No Recent Items")
        }
    }
    LazyColumnFor(items = recents, itemContent = { assignment ->
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
    })

}
