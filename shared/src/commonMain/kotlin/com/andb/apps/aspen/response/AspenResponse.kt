package com.andb.apps.aspen.response

import com.andb.apps.aspen.models.Grade
import com.andb.apps.aspen.models.Subject
import kotlinx.serialization.Serializable

@Serializable
data class AspenResponse(
    val errors: ErrorResponse,
    val data: List<CourseResponse>,
    val asOf: Int
)

fun AspenResponse.toSubjectList(): List<Subject> {
    return data.map { courseResponse ->
        val (last, first) = courseResponse.teacher.split(", ")
        val numberGrade = courseResponse.currentTermGrade.takeWhile { it != ' ' }.toDoubleOrNull()
        val letter = courseResponse.currentTermGrade.takeLastWhile { it != ' ' && it !in '0'..'9' }
        val grade = when {
            numberGrade != null -> Grade.Letter(numberGrade, letter)
            else -> Grade.Ungraded
        }
        Subject(
            id = courseResponse.id,
            name = courseResponse.name,
            teacher = "$first $last",
            config = Subject.Config(courseResponse.id, Subject.Icon.SCHOOL, 0xFF388E3C.toInt()),
            currentGrade = grade,
            assignments = courseResponse.assignments.map { it.toAssignment() }
        )
    }
}

@Serializable
data class ErrorResponse(val id: Int, val title: String?, val details: String?)