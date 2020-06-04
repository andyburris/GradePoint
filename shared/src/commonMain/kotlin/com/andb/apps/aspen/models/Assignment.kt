package com.andb.apps.aspen.models

import com.soywiz.klock.Date

public data class Assignment(
    val id: String,
    val title: String,
    val category: String,
    val due: Date,
    val grade: Grade,
    val subjectName: String,
    val statistics: Statistics
){
    sealed class Statistics{
        object Ungraded : Statistics()
        object Hidden : Statistics()
        class Available(val low: SubjectGrade, val high: SubjectGrade, val average: SubjectGrade, val median: SubjectGrade) : Statistics()
    }
}
