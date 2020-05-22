package com.andb.apps.aspen.models

class Subject(
    val id: String,
    val name: String,
    val teacher: String,
    val config: Config,
    val currentGrade: Grade,
    val assignments: List<Assignment>
) {
    enum class Icon() {
        ART, ATOM, BOOK, CALCULUS, COMPASS, COMPUTER, FLASK, LANGUAGE, MUSIC, PE, SCHOOL
    }

    data class Config(val id: String, val icon: Icon, val color: Int)
}