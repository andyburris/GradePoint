package com.andb.apps.aspen.models


sealed class Grade {
    class Empty(val message: String) : Grade()
    class Missing(val possibleScore: Double) : Grade()
    object Ungraded : Grade()
    class Score(val score: Double, val possibleScore: Double, val letter: String) : Grade()

    override fun toString(): String {
        return when(this){
            is Grade.Score -> "${score}/${possibleScore}"
            is Grade.Empty -> message
            is Grade.Missing -> "Missing/${possibleScore}"
            else -> "Ungraded"
        }
    }
}

sealed class SubjectGrade {
    class Letter(val number: Double, val letter: String?) : SubjectGrade()
    object Ungraded : SubjectGrade()

    override fun toString(): String {
        return when {
            this !is Letter -> ""
            letter==null -> "$number"
            else -> "$number $letter"
        }
    }
}
