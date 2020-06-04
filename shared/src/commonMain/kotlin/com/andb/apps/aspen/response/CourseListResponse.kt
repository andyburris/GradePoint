package com.andb.apps.aspen.response

import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.SubjectGrade
import kotlinx.serialization.Serializable

@Serializable
data class CourseListResponse(
    val errors: ErrorResponse,
    val data: List<CourseResponse>,
    val asOf: Int
)

fun CourseListResponse.toSubjectList(savedConfigs: List<Subject.Config>): List<Subject> {
    return data.map { courseResponse ->
        val (last, first) = courseResponse.teacher.split(", ")
        val config = savedConfigs.find { it.id == courseResponse.id } ?: Subject.Config(courseResponse.id, courseResponse.name.toIcon(), 0xFF388E3C.toInt())
        Subject(
            id = courseResponse.id,
            name = courseResponse.name,
            teacher = "$first $last",
            config = config,
            currentGrade = courseResponse.currentTermGrade.toSubjectGrade(),
            categories = courseResponse.categoryTable,
            assignments = courseResponse.assignments.map { it.toAssignment(courseResponse.name) }
        )
    }
}

fun String.toSubjectGrade(): SubjectGrade {
    val numberGrade = takeWhile { it != ' ' }.toDoubleOrNull()
    val letter = takeLastWhile { it != ' ' && it !in '0'..'9' }
    return when {
        numberGrade != null -> SubjectGrade.Letter(numberGrade, letter)
        else -> SubjectGrade.Ungraded
    }
}

private fun String.toIcon(): Subject.Icon {
    val check = this.toLowerCase()
    return iconStrings.find { pair -> pair.second.any { it in check } }?.first ?: Subject.Icon.SCHOOL
}

private val iconStrings: List<Pair<Subject.Icon, List<String>>> = listOf(
    Subject.Icon.ART to listOf("art"),
    Subject.Icon.ATOM to listOf("chem"),
    Subject.Icon.BOOK to listOf("english"),
    Subject.Icon.CALCULUS to listOf("calc"),
    Subject.Icon.COMPASS to listOf("engineer"),
    Subject.Icon.COMPUTER to listOf("computer"),
    Subject.Icon.FLASK to listOf("lab"),
    Subject.Icon.LANGUAGE to listOf("language", "french", "spanish", "chinese", "german", "latin"),
    Subject.Icon.MUSIC to listOf("music", "band", "orchestra"),
    Subject.Icon.PE to listOf("phys")
)