package com.andb.apps.aspen.models

import com.soywiz.klock.Date

public data class Assignment(
    val id: String,
    val title: String,
    val category: String,
    val due: Date,
    val grade: Grade,
    val scoreLetter: String
)

sealed class Grade {
    class Empty(val message: String) : Grade()
    class Missing(val possibleScore: Double) : Grade()
    object Ungraded : Grade()
    class Score(val score: Double, val possibleScore: Double) : Grade()
    class Letter(val number: Double, val letter: String?) : Grade()
}