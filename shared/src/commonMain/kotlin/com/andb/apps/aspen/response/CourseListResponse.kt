package com.andb.apps.aspen.response

import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.SubjectGrade
import com.andb.apps.aspen.models.Term
import kotlinx.serialization.Serializable

@Serializable
data class CourseListResponse(
    val errors: ErrorResponse,
    val config: ResponseConfig = ResponseConfig("dcps", "4"),
    val data: List<CourseResponse>,
    val asOf: Int
)

fun CourseListResponse.toSubjectList(savedConfigs: List<Subject.Config>, term: Int = config.term.toInt()): List<Subject> {
    return data.map { courseResponse ->
        val (last, first) = courseResponse.teacher.split(", ")
        val config = savedConfigs.find { it.id == courseResponse.id } ?: Subject.Config(courseResponse.id, courseResponse.name.toIcon(), 0xFF4CAF50.toInt())
        Subject(
            id = courseResponse.id,
            name = courseResponse.name,
            teacher = "$first $last",
            config = config,
            terms = listOf(Term.Loading(1), Term.Loading(2), Term.Loading(3), Term.Loading(4)).map {
                when(it.term){
                    term -> courseResponse.toTermGrades(it.term)
                    else -> it
                }
            }
        )
    }
}

fun CourseResponse.toTermGrades(term: Int): Term.WithGrades{
    return Term.WithGrades(
        term = term,
        assignments = assignments.map { it.toAssignment(name) },
        grade = currentTermGrade.toSubjectGrade(),
        categories = categoryTable[term.toString()] ?: throw Error("Category table did not exist for CourseResponse = $this")
    )
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
    Subject.Icon.ART to listOf("art", "draw", "paint", "print", "sculpt", "ceramic"),
    Subject.Icon.ATOM to listOf("chem", "physics"),
    Subject.Icon.BIOLOGY to listOf("bio"),
    Subject.Icon.BOOK to listOf("english", "literature"),
    Subject.Icon.CALCULUS to listOf("calc"),
    Subject.Icon.CAMERA to listOf("photo", "imag", "camera"),
    Subject.Icon.COMPASS to listOf("geom"),
    Subject.Icon.COMPUTER to listOf("computer"),
    Subject.Icon.ECONOMICS to listOf("econ"),
    Subject.Icon.ENGINEERING to listOf("engineer", "mech", "robot"),
    Subject.Icon.FILM to listOf("film", "movie"),
    Subject.Icon.FINANCE to listOf("financ"),
    Subject.Icon.FLASK to listOf("lab"),
    Subject.Icon.GLOBE to listOf("geo", "enviro", "world"),
    Subject.Icon.GOVERNMENT to listOf("gov"),
    Subject.Icon.HEALTH to listOf("health", "med", "safe"),
    Subject.Icon.HISTORY to listOf("history"),
    Subject.Icon.LAW to listOf("law"),
    Subject.Icon.MUSIC to listOf("music", "band", "orchestra"),
    Subject.Icon.NEWS to listOf("news", "journal"),
    Subject.Icon.PE to listOf("physical"),
    Subject.Icon.PSYCHOLOGY to listOf("psych"),
    Subject.Icon.SOCIOLOGY to listOf("soci"),
    Subject.Icon.SPEAKING to listOf("speak", "speech", "debate"),
    Subject.Icon.STATISTICS to listOf("stat", "prob"),
    Subject.Icon.THEATER to listOf("theater", "drama", "stage"),
    Subject.Icon.TRANSLATE to listOf("language", "french", "spanish", "chinese", "german", "latin", "arabic", "italian", "russian"),
    Subject.Icon.WRITING to listOf("writ", "poet")
)