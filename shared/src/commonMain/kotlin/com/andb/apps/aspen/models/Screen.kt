package com.andb.apps.aspen.models

import com.andb.apps.aspen.response.RecentAssignmentResponse

sealed class Screen {
    data class Login(val error: String? = null) : Screen()

    //object Loading : Screen()
    data class Home(
        val subjects: List<com.andb.apps.aspen.models.Subject>,
        val recentState: RecentState,
        val tab: HomeTab,
        val terms: List<TermState>,
        val selectedTerm: Int
    ) : Screen() {
        fun updatedTerm(term: Int, newTermState: TermState): List<TermState>{
            return terms.map { if (it.term == term) newTermState else it }
        }
        companion object {
            val Loading: Home get() = Home(listOf(), RecentState.Loading, HomeTab.SUBJECTS, (1..4).map { TermState.Loading(it) }, 1)
        }
    }

    data class Subject(val subject: com.andb.apps.aspen.models.Subject, val term: Int) : Screen()
    data class Assignment(val assignment: com.andb.apps.aspen.models.Assignment) : Screen()
    object Test : Screen()
}

sealed class RecentState {
    data class Error(val error: AspenError) : RecentState()
    object Loading : RecentState()
    data class Data(val recents: List<RecentAssignmentResponse>) : RecentState()
}

sealed class TermState(open val term: Int) {

    data class Error(override val term: Int, val error: AspenError) : TermState(term)
    data class Loading(override val term: Int) : TermState(term)
    data class Data (override val term: Int) : TermState(term)
}

enum class AspenError {
    OFFLINE, OTHER
}

sealed class OfflineAt {
    object Login : OfflineAt()
    object Current : OfflineAt()
    data class Term(val term: Int) : OfflineAt()
    object Recents: OfflineAt()
}

enum class HomeTab {
    SUBJECTS, RECENTS, SETTINGS
}


fun List<RecentAssignmentResponse>.toAssignments(allAssignments: List<Assignment>) = mapNotNull { recent -> allAssignments.find { it.id==recent.id } }

fun List<Subject>.allAssignments() = flatMap { subject -> subject.terms.filterIsInstance<Term.WithGrades>().flatMap { it.assignments } }