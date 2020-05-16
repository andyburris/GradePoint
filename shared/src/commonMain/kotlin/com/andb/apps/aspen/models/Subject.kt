package com.andb.apps.aspen.models

class Subject(
    val id: String,
    val name: String,
    val teacher: String,
    val icon: Icon,
    val color: Int,
    val currentGrade: Double,
    val currentGradeLetter: Char
) {
    enum class Icon() {
        ART, ATOM, BOOK, CALCULUS, COMPASS, COMPUTER, FLASK, LANGUAGE, MUSIC, PE, SCHOOL
    }
}