package com.andb.apps.aspen.response

import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Grade
import com.andb.apps.aspen.models.SubjectGrade
import com.soywiz.klock.Date
import kotlinx.serialization.Serializable

@Serializable
data class AssignmentResponse(
    val id: String,
    val name: String,
    val dateAssigned: String,
    val dateDue: String,
    val credit: String,
    val feedback: String?,
    val score: String?,
    val possibleScore: String?,
    val gradeLetter: String?,
    val category: String,
    val stats: Map<String, String>?
)

fun AssignmentResponse.toAssignment(courseName: String): Assignment {
    val grade = when {
        score != null && possibleScore != null -> Grade.Score(
            score.toDouble(),
            possibleScore.toDouble(),
            gradeLetter ?: ""
        )
        credit.contains("Ungraded") -> Grade.Empty("Ungraded")
        credit.contains("Msg") || credit.contains("Abs") -> {
            val words = credit.split("\\s+".toRegex()).map { word ->
                word.replace("""^[,\.]|[,\.]$""".toRegex(), "")
            }
            val possibleGradeIndex = words.indexOfLast { it.startsWith("(") } - 1
            Grade.Missing(words[possibleGradeIndex].toDouble())
        }
        else -> Grade.Empty(credit)
    }
    val (month, day, year) = dateDue.split("/").map { it.toInt() }
    return Assignment(id, name, category, Date(year, month, day), grade, courseName, stats.toStatistics())
}

private fun Map<String, String>?.toStatistics(): Assignment.Statistics {
    return when {
        this == null -> Assignment.Statistics.Hidden
        this.any { it.value.isEmpty() } -> Assignment.Statistics.Ungraded
        else -> Assignment.Statistics.Available(
            this["low"]?.toSubjectGrade() ?: SubjectGrade.Ungraded,
            this["high"]?.toSubjectGrade() ?: SubjectGrade.Ungraded,
            this["average"]?.toSubjectGrade() ?: SubjectGrade.Ungraded,
            this["median"]?.toSubjectGrade() ?: SubjectGrade.Ungraded
        )
    }
}