package com.andb.apps.aspen.models

import com.andb.apps.aspen.response.RecentAssignmentResponse

sealed class Screen {
    object Login : Screen()

    //object Loading : Screen()
    data class Home(
        val subjects: List<com.andb.apps.aspen.models.Subject>,
        val recentItems: List<RecentAssignmentResponse>,
        val tab: HomeTab,
        val term: Int
    ) : Screen()

    data class Subject(val subject: com.andb.apps.aspen.models.Subject, val term: Int) : Screen()
    data class Assignment(val assignment: com.andb.apps.aspen.models.Assignment) : Screen()
    object Test : Screen()
}

enum class HomeTab {
    SUBJECTS, RECENTS, SETTINGS
}


val Screen.Home.recents: List<Assignment>
    get() {
        val allAssignments = subjects.allAssignments()
        return recentItems.mapNotNull { recent -> allAssignments.find { it.id==recent.id } }
    }

private fun List<Subject>.allAssignments() =
    flatMap { subject -> subject.terms.filterIsInstance<Term.WithGrades>().flatMap { it.assignments } }