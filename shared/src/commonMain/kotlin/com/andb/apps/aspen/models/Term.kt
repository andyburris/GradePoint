package com.andb.apps.aspen.models

sealed class Term(open val term: Int) {
    class OutOfSession(term: Int) : Term(term)
    data class Loading(override val term: Int) : Term(term)
    data class WithGrades(
        override val term: Int,
        val assignments: List<Assignment>,
        val grade: SubjectGrade,
        val categories: List<Category>
    ) : Term(term)
}