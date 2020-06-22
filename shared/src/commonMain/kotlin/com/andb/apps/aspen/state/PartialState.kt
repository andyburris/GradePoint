package com.andb.apps.aspen.state

import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.response.RecentAssignmentResponse

sealed class PartialState

sealed class HomeState : PartialState()

data class SubjectListState(val subjects: List<Subject>) : HomeState()
data class RecentAssignmentsState(val recents: List<RecentAssignmentResponse>) : HomeState()
data class TabState(val tab: HomeTab) : HomeState()