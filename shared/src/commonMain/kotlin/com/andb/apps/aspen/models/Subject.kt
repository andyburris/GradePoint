package com.andb.apps.aspen.models

import com.andb.apps.aspen.db.SubjectConfig

data class Subject(
    val id: String,
    val name: String,
    val teacher: String,
    val config: Config,
    val currentGrade: SubjectGrade,
    val categories: Map<String, List<Category>>,
    val assignments: List<Assignment>
) {
    enum class Icon() {
        ART, ATOM, BOOK, CALCULUS, COMPASS, COMPUTER, FLASK, LANGUAGE, MUSIC, PE, SCHOOL
    }

    data class Config(val id: String, val icon: Icon, val color: Int)
}

fun SubjectConfig.toConfig() = Subject.Config(id, Subject.Icon.valueOf(iconName), color.toInt())